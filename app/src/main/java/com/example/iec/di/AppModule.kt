package com.example.iec.di

import com.example.iec.state.ApplicationStateHolder
import com.example.iec.state.ApplicationStateHolderImpl
import com.example.iec.state.LoadingStateHolder
import com.example.iec.state.LoadingStateHolderImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideLoadingStateHolder(loadingStateHolderImpl: LoadingStateHolderImpl): LoadingStateHolder

    @Binds
    @Singleton
    abstract fun provideApplicationStateHolder(applicationStateHolderImpl: ApplicationStateHolderImpl): ApplicationStateHolder
}