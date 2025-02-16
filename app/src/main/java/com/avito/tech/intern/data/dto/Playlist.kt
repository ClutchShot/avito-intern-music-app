package com.avito.tech.intern.data.dto

import com.google.gson.annotations.SerializedName

data class Playlist(
    val id: Long,
    val title: String,
    val public: Boolean,
    @SerializedName("nb_tracks")
    val nbTracks: Int,
    val link: String,
    val picture: String,
    @SerializedName("picture_small")
    val pictureSmall: String,
    @SerializedName("picture_medium")
    val pictureMedium: String,
    @SerializedName("picture_big")
    val pictureBig: String,
    @SerializedName("picture_xl")
    val pictureXl: String,
    val checksum: String,
    val tracklist: String,
    @SerializedName("creation_date")
    val creationDate: String,
    @SerializedName("md5_image")
    val md5Image: String,
    @SerializedName("picture_type")
    val pictureType: String,
    val user: User,
    val type: String

)