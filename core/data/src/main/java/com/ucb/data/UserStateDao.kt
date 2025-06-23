package com.ucb.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ucb.model.User
import com.ucb.model.UserState

@Dao
interface UserStateDao {
    @Query("SELECT isLoggedIn FROM user_state LIMIT 1")
    suspend fun isLoggedIn(): Boolean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setUserState(userState: UserState)

    @Query("DELETE FROM user_state")
    suspend fun deleteAll()

    @Query("SELECT user_state.id FROM user_state")
    suspend fun getUserState(): Int

    @Query("SELECT user_table.* FROM user_state JOIN user_table ON user_state.id = user_table.userId")
    fun getInfoUser(): User

}