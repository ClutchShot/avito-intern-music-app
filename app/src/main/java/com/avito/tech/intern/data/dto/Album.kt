package com.avito.tech.intern.data.dto

import com.google.gson.annotations.SerializedName

data class Album(
    val id: Long,
    val title: String,
    val cover: String,
    @SerializedName("cover_small")
    val coverSmall: String,
    @SerializedName("cover_medium")
    val coverMedium: String,
    @SerializedName("cover_big")
    val coverBig: String,
    @SerializedName("cover_xl")
    val coverXl: String,
    @SerializedName("md5_image")
    val md5Image: String,
    val tracklist: String,
    val type: String

)