package com.ucb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_state")
data class UserState(
    @PrimaryKey val id: Int = 0,
    val isLoggedIn: Boolean
)