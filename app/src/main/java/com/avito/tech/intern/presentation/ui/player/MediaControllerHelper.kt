package com.avito.tech.intern.presentation.ui.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors

class MediaControllerHelper(context: Context, sessionToken: SessionToken) {
    private lateinit var mediaController: MediaController

    init {
        val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture.addListener(
            { mediaController = controllerFuture.get() },
            MoreExecutors.directExecutor()
        )
    }

    fun play(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        mediaController.setMediaItem(mediaItem)
        mediaController.prepare()
        mediaController.play()
    }

    fun pause() {
        mediaController.pause()
    }

    fun seekTo(position: Long) {
        mediaController.seekTo(position)
    }

    fun release() {
        mediaController.release()
    }

    fun getCurrentPosition(): Long {
        return mediaController.currentPosition ?: 0
    }

    fun getDuration(): Long {
        return mediaController.duration ?: 0
    }

    fun isPlaying(): Boolean {
        return mediaController.isPlaying ?: false
    }
}