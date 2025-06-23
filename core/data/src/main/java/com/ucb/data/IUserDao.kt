package com.ucb.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ucb.model.Genre
import com.ucb.model.User
import com.ucb.model.UserGenreCrossRef

@Dao
interface IUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: Genre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserGenreCrossRef(userGenreCrossRef: UserGenreCrossRef)

    @Query("SELECT genreId FROM genre_table WHERE name = :name")
    suspend fun getGenreIdByName(name: String): Int

    @Query("SELECT COUNT(*) FROM user_table WHERE email = :email AND password = :password")
    suspend fun loginUser(email: String, password: String) : Int

    @Query("SELECT COUNT(*) FROM user_table WHERE username = :username AND password = :password")
    suspend fun loginUser2(username: String, password: String) : Int

    @Query("SELECT * FROM user_table WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT userId FROM user_table WHERE username = :username")
    suspend fun getIdByUsername(username: String): Int
}