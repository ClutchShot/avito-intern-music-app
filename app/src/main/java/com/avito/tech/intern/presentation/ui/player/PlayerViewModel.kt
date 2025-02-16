package com.avito.tech.intern.presentation.ui.player

import android.app.Application
import android.content.ComponentName
import android.content.Context
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.avito.tech.intern.data.database.entity.Track
import com.avito.tech.intern.data.repository.LocalRepository
import com.avito.tech.intern.presentation.common.DownloadState
import com.avito.tech.intern.presentation.nav.TrackDetails
import com.avito.tech.intern.service.PlaybackService
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val application: Application,
    private val localRepository: LocalRepository
) : ViewModel() {

    private var mediaController: MediaController? = null
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying
    private var _trackDetails: TrackDetails? = null
    private var _mediaItem: MediaItem? = null

    private val _downloadState = MutableStateFlow<DownloadState>(DownloadState.Ready)
    val downloadState = _downloadState

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition

    fun setTrack(trackDetails: TrackDetails) {
        _trackDetails = trackDetails
        _mediaItem = MediaItem.Builder()
            .setUri(trackDetails.uri)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(trackDetails.title)
                    .setArtist(trackDetails.artist)
                    .build()
            ).build()
    }

    fun setLocalTrack(trackDetails: TrackDetails){
        _trackDetails = trackDetails
        val file = File(application.filesDir, trackDetails.uri)

        var uri = file.toUri()
        _mediaItem = MediaItem.Builder()
            .setUri(file.toUri())
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(trackDetails.title)
                    .setArtist(trackDetails.artist)
                    .build()
            ).build()
    }




    fun initializeMediaController(context: Context) {
        viewModelScope.launch {
            val sessionToken =
                SessionToken(application, ComponentName(application, PlaybackService::class.java))
            val controllerFuture = MediaController.Builder(application, sessionToken).buildAsync()
            controllerFuture.addListener(
                { mediaController = controllerFuture.get() },
                MoreExecutors.directExecutor()
            )
        }
    }

    fun play() {
        mediaController?.let { mc ->
            _mediaItem?.let { mi ->
                mc.setMediaItem(mi)
                mc.prepare()
                mc.play()
                _isPlaying.value = !isPlaying.value
            }
        }
    }

    fun pause() {
        mediaController?.let {
            it.pause()
            _isPlaying.value = !isPlaying.value
        }
    }

    override fun onCleared() {
        mediaController?.release()
        mediaController = null
        super.onCleared()
    }

    fun downloadFileToInternalStorage() {
        viewModelScope.launch(Dispatchers.IO) {
            _downloadState.update { DownloadState.Loading }
            val client = OkHttpClient()
            val request = Request.Builder().url(_trackDetails!!.uri).build()
            val response: Response = client.newCall(request).execute()
            val fileName = "${_trackDetails!!.title}.mp3"
            if (response.isSuccessful) {
                saveFileToStorage(response.body, fileName)
                _downloadState.update { DownloadState.Success }

                _trackDetails?.let {
                    localRepository.insert(
                        Track(
                            it.id,
                            it.title,
                            it.artist,
                            it.coverBig,
                            fileName
                        )
                    )
                }
            }
        }
    }

    private fun saveFileToStorage(body: ResponseBody?, fileName: String) {
        body?.let {
            try {
                val inputStream = it.byteStream()
                val file = File(application.filesDir, fileName)
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun isTrackLocal(trackDetails: TrackDetails): Boolean{
        return localRepository.getById(trackDetails.id) != null
    }

}

