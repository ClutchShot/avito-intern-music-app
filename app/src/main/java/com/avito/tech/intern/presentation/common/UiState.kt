package com.avito.tech.intern.presentation.common

import android.net.Uri

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

sealed class DownloadState {
    object Ready : DownloadState()
    object Loading : DownloadState()
    object Success : DownloadState()
    object Error : DownloadState()
}