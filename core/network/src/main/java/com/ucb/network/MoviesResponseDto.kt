package com.ucb.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesResponseDto(
    @Json(name = "results")
    val results:  List<MovieResponseDto>
){}