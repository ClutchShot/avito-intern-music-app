package com.avito.tech.intern.data.repository

import androidx.annotation.WorkerThread
import com.avito.tech.intern.data.database.dao.TrackDao
import com.avito.tech.intern.data.database.entity.Track
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query
import javax.inject.Inject

class LocalRepository @Inject constructor(private val trackDao: TrackDao) {

    val allTracks: Flow<List<Track>> = trackDao.getAll()

    @WorkerThread
    suspend fun insert(track: Track) {
        trackDao.insert(track)
    }

    @WorkerThread
    suspend fun getById(id : Long): Track? {
        return trackDao.getById(id)
    }

    @WorkerThread
    suspend fun updateTrack(track: Track) {
        trackDao.updateTrack(track)
    }

    @WorkerThread
    suspend fun deleteTrackById(id: Long) {
        trackDao.deleteTrackById(id)
    }


    @WorkerThread
    fun getSearch(query: String) : Flow<List<Track>> {
        return trackDao.getByTitleOrArtist(query)
    }


}