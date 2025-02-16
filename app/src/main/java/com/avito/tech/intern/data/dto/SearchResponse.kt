package com.avito.tech.intern.data.dto

data class SearchResponse(
    var data: List<Track>,
    var total: Int,
    var next: String
)