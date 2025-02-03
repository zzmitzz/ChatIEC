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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


object PreferenceKeys {
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_PASSWORD = stringPreferencesKey("user_password")
}


interface DataStoreHelper {
    suspend fun saveData(key: Preferences.Key<String>, value: String)
    fun readData(key: Preferences.Key<String>): Flow<String?>
    suspend fun clearData(vararg key: Preferences.Key<String>)
}


class DataStoreHelperImpl @Inject constructor(
    @ApplicationContext val context: Context
) : DataStoreHelper {

    private val dataStore = context.dataStore


    override suspend fun saveData(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preference ->
            preference[key] = value
        }.also {
            Log.d("DataStoreHelper", "Save data successfully ${key.name} = $value")
        }
    }

    override fun readData(key: Preferences.Key<String>): Flow<String?> {
        return dataStore.data.map {
            it[key]
        }
    }

    override suspend fun clearData(vararg keys: Preferences.Key<String>) {
        keys.forEach { key ->
            dataStore.edit { it.remove(key) }
        }
    }


}