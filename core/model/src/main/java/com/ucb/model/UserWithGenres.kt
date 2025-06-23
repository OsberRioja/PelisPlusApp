package com.ucb.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithGenres(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "genreId",
        associateBy = Junction(UserGenreCrossRef::class)
    )
    val genres: List<Genre>
)