package com.ucb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre_table")
data class Genre (
    @PrimaryKey
    val genreId: Int,
    val name: String
)