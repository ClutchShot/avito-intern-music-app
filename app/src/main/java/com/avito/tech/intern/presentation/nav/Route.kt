package com.avito.tech.intern.presentation.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.PlayCircleFilled
import androidx.compose.material.icons.sharp.Search
import androidx.compose.ui.graphics.vector.ImageVector


object Route {
    const val SEARCH = "searh"
    const val DOWLOADED = "downloaded"
    const val TRACK_PLAYER = "track_player/{id}"
}

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object Search : Screen(route = Route.SEARCH, title = "Search", icon =  Icons.Sharp.Search)
    object Dowloaded : Screen(route = Route.DOWLOADED, title = "Dowloaded", icon = Icons.Sharp.Home)
    object TrackPlayer : Screen(route = Route.TRACK_PLAYER, title = "Player", icon = Icons.Sharp.PlayCircleFilled){
        fun setTrack(trackDetails: TrackDetails) = "track_player/$trackDetails"
    }
}