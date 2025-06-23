package com.ucb.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieWithGenres(
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<Genre>
)

data class GenreWithMovies(
    @Embedded val genre: Genre,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "movieId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val movies: List<Movie>
)