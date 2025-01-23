package com.example.iec.di

import com.example.iec.DataStoreHelper
import com.example.iec.DataStoreHelperImpl
import com.example.iec.data.remote.AuthRemote
import com.example.iec.data.remote.MessageRemote
import com.example.iec.data.remote.NoteRemote
import com.example.iec.data.repository.AuthRepository
import com.example.iec.data.repository.AuthRepositoryImpl
import com.example.iec.data.repository.MessageRepository
import com.example.iec.data.repository.MessageRepositoryImpl
import com.example.iec.data.repository.NoteRepository
import com.example.iec.data.repository.NoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.math.log


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideNoteRetrofit(): Retrofit {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()


        return Retrofit.Builder()
            .baseUrl("https://ap-southeast-1.aws.data.mongodb-api.com/app/data-pkcss/endpoint/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteService(retrofit: Retrofit): NoteRemote {
        return retrofit.create(NoteRemote::class.java)
    }

    @Provides
    @Singleton
    fun provideMessService(retrofit: Retrofit): MessageRemote {
        return retrofit.create(MessageRemote::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthRemote {
        return retrofit.create(AuthRemote::class.java)
    }

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging)
            install(WebSockets)
        }
    }
}


@InstallIn(SingletonComponent::class)
@Module
abstract class RepoModule {
    @Binds
    abstract fun provideNoteRepository(noteRemote: NoteRepositoryImpl): NoteRepository

    @Binds
    abstract fun provideMessRepository(messageRemote: MessageRepositoryImpl): MessageRepository

    @Binds
    abstract fun provideAuthRepository(authRemote: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideDataStore(dataStoreHelper: DataStoreHelperImpl): DataStoreHelper
}