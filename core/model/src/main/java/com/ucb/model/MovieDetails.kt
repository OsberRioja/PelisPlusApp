package com.ucb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_details_table")
data class MovieDetails(
    @PrimaryKey val movieId: Int,
    val vote: Int,
    val isFavorite: Boolean
)