package com.avito.tech.intern.data.dto

import com.avito.tech.intern.presentation.nav.TrackDetails
import com.avito.tech.intern.presentation.ui.model.TrackUI
import com.google.gson.annotations.SerializedName

data class Track(
    val id: Long,
    val readable : Boolean? = null,
    val title: String,
    @SerializedName("title_short")
    val titleShort: String,
    @SerializedName("title_version")
    val titleVersion: String,
    val link: String,
    val duration: Int,
    val rank: Int,
    @SerializedName("explicit_lyrics")
    val explicitLyrics: Boolean,
    @SerializedName("explicit_content_lyrics")
    val explicitContentLyrics: Int,
    @SerializedName("explicit_content_cover")
    val explicitContentCover: Int,
    val preview: String,
    val md5Image: String,
    val position: Int? = null,
    val artist: Artist,
    val album: Album,
    val type: String
)


fun Track.toTrackUI(): TrackUI{
    return TrackUI(id = this.id, title = this.title, artist = this.artist.name, uri = this.preview, cover = this.album.cover)
}

