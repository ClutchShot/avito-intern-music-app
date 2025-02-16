package com.avito.tech.intern.data.dto

import com.google.gson.annotations.SerializedName

data class Albums(
    val data  : List<Album>,
    val total : Int
)
