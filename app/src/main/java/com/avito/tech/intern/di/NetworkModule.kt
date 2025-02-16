package com.avito.tech.intern.di

import com.avito.tech.intern.data.network.DeezerAPI
import com.avito.tech.intern.data.network.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideDeezerAPI(): DeezerAPI = NetworkClient.instanceAPI
}
