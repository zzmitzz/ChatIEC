package com.example.iec.di

import com.example.iec.data.remote.MessageRemote
import com.example.iec.data.remote.NoteRemote
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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideNoteRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
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
abstract class RepoModule{
    @Binds
    abstract fun provideNoteRepository(noteRemote: NoteRepositoryImpl): NoteRepository
    @Binds
    abstract fun provideMessRepository(messageRemote: MessageRepositoryImpl): MessageRepository
}