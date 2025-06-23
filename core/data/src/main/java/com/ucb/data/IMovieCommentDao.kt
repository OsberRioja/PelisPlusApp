package com.ucb.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ucb.model.MovieComment

@Dao
interface IMovieCommentDao {
    @Insert
    suspend fun insertComment(comment: MovieComment)

    @Query("SELECT * FROM movie_comments WHERE movieId = :movieId")
    suspend fun getCommentsForMovie(movieId: Int): List<MovieComment>

    @Delete
    suspend fun deleteComment(comment: MovieComment)

    @Update
    suspend fun updateComment(comment: MovieComment)
}