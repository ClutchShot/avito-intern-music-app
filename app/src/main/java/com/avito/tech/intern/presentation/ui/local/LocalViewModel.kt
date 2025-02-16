package com.avito.tech.intern.presentation.ui.local

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avito.tech.intern.data.database.entity.Track
import com.avito.tech.intern.data.database.entity.toTrackUI
import com.avito.tech.intern.data.repository.LocalRepository
import com.avito.tech.intern.presentation.common.UiState
import com.avito.tech.intern.presentation.ui.model.TrackUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalViewModel @Inject constructor(
    private val localRepository: LocalRepository
): ViewModel() {


    val _tracks = localRepository.allTracks
    private val _uiState = MutableStateFlow<UiState<List<TrackUI>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<TrackUI>>> = _uiState

    private val _query = MutableStateFlow("")
    val query : StateFlow<String> = _query

    init {
        viewModelScope.launch {
            _tracks.collect { trackList ->
                _uiState.update {
                    if (trackList.isNotEmpty()) {
                        UiState.Success(trackList.map { it.toTrackUI() })
                    } else {
                        UiState.Error("Not found")
                    }
                }
            }
        }
    }

    fun getSearch() {
        viewModelScope.launch {
            delay(1000)
            if (_query.value.isNotBlank()) {
                localRepository.getSearch(_query.value).collect { trackList ->
                    _uiState.update {
                        if (trackList.isNotEmpty()){
                            UiState.Success(trackList.map { it.toTrackUI() })
                        }
                        else{
                            UiState.Error("Not found")
                        }
                    }
                }
            }
        }
    }

    fun updateQuery(query: String) {
        viewModelScope.launch {
            _query.update { query }
            if (query.isBlank()) {
                _tracks.collect { trackList ->
                    _uiState.update {
                        if (trackList.isNotEmpty()) {
                            UiState.Success(trackList.map { it.toTrackUI() })
                        } else {
                            UiState.Error("No tracks")
                        }
                    }
                }
            }
            else{
                getSearch()
            }
        }
    }
}