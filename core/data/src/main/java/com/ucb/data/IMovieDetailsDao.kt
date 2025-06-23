package com.ucb.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ucb.model.MovieDetails

@Dao
interface IMovieDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movieDetails: MovieDetails)

    @Query("SELECT * FROM movie_details_table WHERE movieId = :movieId LIMIT 1")
    suspend fun getMovieDetails(movieId: Int): MovieDetails

    @Update
    suspend fun updateMovieDetails(movieDetails: MovieDetails)
}
