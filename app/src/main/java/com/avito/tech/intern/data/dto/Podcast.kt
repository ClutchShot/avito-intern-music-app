package com.avito.tech.intern.data.dto

import com.google.gson.annotations.SerializedName

data class Podcast(
    val id: Long,
    val title: String,
    val description: String,
    val available: Boolean,
    val fans: Int,
    val link: String,
    val share: String,
    val picture: String,
    @SerializedName("picture_small")
    val pictureSmall: String,
    @SerializedName("picture_medium")
    val pictureMedium: String,
    @SerializedName("picture_big")
    val pictureBig: String,
    @SerializedName("picture_xl")
    val pictureXl: String,
    val type: String

)