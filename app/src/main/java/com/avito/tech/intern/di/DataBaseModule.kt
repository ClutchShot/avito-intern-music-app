package com.avito.tech.intern.di

import android.content.Context
import androidx.room.Room
import com.avito.tech.intern.data.database.AppDatabase
import com.avito.tech.intern.data.database.dao.TrackDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    private const val DB_NAME = "track_database"

    @Provides
    @Singleton
    fun provideTrackDatabase(@ApplicationContext appContext: Context) : AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    fun provideTrackDao(database: AppDatabase) : TrackDao = database.trackDao()

}