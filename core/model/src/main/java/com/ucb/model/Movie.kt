package com.ucb.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "movie_table")
data class Movie(
    @PrimaryKey val movieId: Int,
    val title: String,
    val description: String,
    val posterPath: String,
    val voteAverage: Double,
    val releaseDate: String,
    val voteSelf: Int,
    val newVote: Double
)