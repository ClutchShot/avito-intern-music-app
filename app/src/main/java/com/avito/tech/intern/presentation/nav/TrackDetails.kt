package com.avito.tech.intern.presentation.nav

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class TrackDetails(
    val id: Long,
    val title : String,
    val artist : String,
    val uri: String,
    val coverBig: String
)
