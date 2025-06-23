package com.ucb.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "movie_comments",
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = ["movieId"],
        childColumns = ["movieId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class MovieComment(
    @PrimaryKey(autoGenerate = true) val commentId: Int = 0,
    val movieId: Int,
    val comment: String,
)
