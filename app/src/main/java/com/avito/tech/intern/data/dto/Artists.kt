package com.avito.tech.intern.data.dto

import com.google.gson.annotations.SerializedName

data class Artists(
    var data: List<Artist>,
    var total: Int
)