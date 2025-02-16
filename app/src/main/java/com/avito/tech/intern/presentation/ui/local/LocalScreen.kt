package com.avito.tech.intern.presentation.ui.local

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.avito.tech.intern.presentation.common.TrackList
import com.avito.tech.intern.presentation.nav.TrackDetails

@Composable
fun LocalScreen(
    localViewModel: LocalViewModel = hiltViewModel(),
    onTrackClick : (TrackDetails) -> Unit){

    val trackList = localViewModel.uiState.collectAsState()
    val query by localViewModel.query.collectAsState()

    TrackList(trackList.value, query, {value -> localViewModel.updateQuery(value) }, onTrackClick)
}