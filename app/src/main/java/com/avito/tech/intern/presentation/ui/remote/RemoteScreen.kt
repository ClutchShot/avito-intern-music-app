package com.avito.tech.intern.presentation.ui.remote

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.avito.tech.intern.presentation.common.TrackList
import com.avito.tech.intern.presentation.nav.TrackDetails


@Composable
fun RemoteScreen(
    searchViewModel: RemoteViewModel = hiltViewModel(),
    onTrackClick : (TrackDetails) -> Unit){

    val trackList = searchViewModel.tracks.collectAsState()
    val query by searchViewModel.query.collectAsState()

    TrackList(trackList.value, query, {value -> searchViewModel.updateQuery(value) }, onTrackClick)
}