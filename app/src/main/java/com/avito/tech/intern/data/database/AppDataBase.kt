package com.avito.tech.intern.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.avito.tech.intern.data.database.dao.TrackDao
import com.avito.tech.intern.data.database.entity.Track

@Database(entities = [Track::class,], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
}