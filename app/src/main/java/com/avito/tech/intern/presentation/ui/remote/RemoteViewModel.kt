package com.avito.tech.intern.presentation.ui.remote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avito.tech.intern.data.dto.Track
import com.avito.tech.intern.data.dto.toTrackUI

import com.avito.tech.intern.data.repository.RemoteRepository
import com.avito.tech.intern.presentation.common.DownloadState
import com.avito.tech.intern.presentation.common.UiState
import com.avito.tech.intern.presentation.ui.model.TrackUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    init {
        getDeezerChart()
    }

    private val _tracksChart = MutableStateFlow<UiState<List<TrackUI>>>(UiState.Loading)

    private val _tracks = MutableStateFlow<UiState<List<TrackUI>>>(UiState.Loading)
    val tracks: StateFlow<UiState<List<TrackUI>>> = _tracks

    private val _query = MutableStateFlow("")
    val query : StateFlow<String> = _query

    private val _downloadState = MutableStateFlow<DownloadState>(DownloadState.Ready)
    val downloadState: StateFlow<DownloadState> = _downloadState


    fun getDeezerChart() {
        viewModelScope.launch {
            val response = remoteRepository.getChart()
            if (response.isSuccessful) {
                _tracksChart.update {
                    UiState.Success(
                        response.body()!!.tracks.data.map{
                            it.toTrackUI()
                        }
                    )
                }
                _tracks.update {
                    UiState.Success(
                        response.body()!!.tracks.data.map { it.toTrackUI() }
                    )
                }
            }
            else{
                _tracksChart.update { UiState.Error("Error: ${response.code()} - ${response.errorBody()}") }
            }

        }
    }

    fun getSearch() {

        viewModelScope.launch {
            delay(1000)
            if (_query.value.isNotBlank()){
                val response = remoteRepository.getSearch(_query.value)
                if (response.isSuccessful) {
                    _tracks.update {
                        UiState.Success(
                            response.body()!!.data.map { it.toTrackUI() }
                        )
                    }
                } else {
                    _tracks.update { UiState.Error("Error: ${response.code()} - ${response.errorBody()}") }
                }
            }
        }

    }

    fun updateQuery(query: String) {
        _query.update { query }
        if (query.isBlank()){
            _tracks.update { _tracksChart.value }
        }
        else{
            getSearch()
        }
    }


}
