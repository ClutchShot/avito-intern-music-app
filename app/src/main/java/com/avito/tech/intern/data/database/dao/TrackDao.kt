package com.avito.tech.intern.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.avito.tech.intern.data.database.entity.Track
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
    @Query("SELECT * FROM track")
    fun getAll(): Flow<List<Track>>

    @Insert
    suspend fun insert(track: Track)

    @Query("DELETE FROM track WHERE id = :id")
    suspend fun deleteTrackById(id: Long)

    @Query("SELECT * FROM track WHERE id = :id")
    suspend fun getById(id: Long): Track?

    @Update
    suspend fun updateTrack(track: Track)

    @Query("SELECT * FROM track WHERE LOWER(title) LIKE LOWER(:query) OR LOWER(artist) LIKE LOWER(:query)")
    fun getByTitleOrArtist(query: String): Flow<List<Track>>
}