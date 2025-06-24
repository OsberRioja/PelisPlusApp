package com.ucb.network

import com.ucb.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponseDto(
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "poster_path")
    val poster_path: String,
    @Json(name = "genre_ids")
    val genre_ids: List<Int>,
    @Json(name = "vote_average")
    val  vote_average: Double,
    @Json(name = "release_date")
    val release_date: String
){
    fun toMovie(): Movie {
        return Movie(
            movieId = id.toInt(),
            title = title,
            description = overview,
            posterPath = poster_path,
            voteAverage = vote_average,
            releaseDate = release_date,
            voteSelf = 0,
            newVote = 0.0
        )
    }
}
