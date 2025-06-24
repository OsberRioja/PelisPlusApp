package com.ucb.repository

import android.content.Context
import com.ucb.data.AppRoomDatabase
import com.ucb.model.MovieComment

class MovieCommentRepository( val context: Context) {
    val dao = AppRoomDatabase.getDatabase(context).movieCommentDao()
    suspend fun insertComment(comment: MovieComment) {
        dao.insertComment(comment)
    }
    suspend fun getCommentsForMovie(movieId: Int): List<MovieComment> {
        return dao.getCommentsForMovie(movieId)
    }
    suspend fun deleteComment(comment: MovieComment) {
        dao.deleteComment(comment)
    }

    suspend fun updateComment(comment: MovieComment) {
        dao.updateComment(comment)
    }
}
