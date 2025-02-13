package com.example.iec


import android.content.Context
import android.preference.Preference
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


object PreferenceKeys {
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_PASSWORD = stringPreferencesKey("user_password")
}


interface DataStoreHelper {
    fun saveData(key: Preferences.Key<String>, value: String,  scope: CoroutineScope)
    fun readData(key: Preferences.Key<String>): Flow<String?>
    fun clearData(vararg key: Preferences.Key<String>, scope: CoroutineScope)
}


class DataStoreHelperImpl @Inject constructor(
    @ApplicationContext val context: Context
) : DataStoreHelper {

    private val dataStore = context.dataStore


    override fun saveData(key: Preferences.Key<String>, value: String, scope: CoroutineScope) {
        scope.launch {
            dataStore.edit { preference ->
                preference[key] = value
            }
        }
    }

    override fun readData(key: Preferences.Key<String>): Flow<String?> {
        return dataStore.data.map {
            it[key]
        }
    }

    override fun clearData(vararg keys: Preferences.Key<String>, scope: CoroutineScope) {
        scope.launch {
            keys.forEach { key ->
                dataStore.edit { it.remove(key) }
            }
        }
    }

}

fun Context.DataStoreHelper(): DataStoreHelper {
    return DataStoreHelperImpl(this)
}