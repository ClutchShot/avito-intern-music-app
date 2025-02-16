package com.avito.tech.intern.data.dto

import com.google.gson.annotations.SerializedName

data class Artist(
    val id: Int,
    val name: String,
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
    val radio: Boolean,
    val tracklist: String,
    val type: String
)