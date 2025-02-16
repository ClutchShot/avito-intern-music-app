package com.avito.tech.intern.di

import com.avito.tech.intern.data.database.dao.TrackDao
import com.avito.tech.intern.data.network.DeezerAPI
import com.avito.tech.intern.data.repository.LocalRepository
import com.avito.tech.intern.data.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteRepository(deezerAPI: DeezerAPI): RemoteRepository = RemoteRepository(deezerAPI)

    @Provides
    @Singleton
    fun provideLocalRepository(trackDao: TrackDao): LocalRepository = LocalRepository(trackDao)
}