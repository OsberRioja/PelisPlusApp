package com.ucb.repository

import android.content.Context
import com.ucb.data.AppRoomDatabase
import com.ucb.model.MovieDetails

class MovieDetailsRepository(val context: Context) {
    val dao = AppRoomDatabase.getDatabase(context).movieDetailsDao()
    suspend fun insertMovieDetails(movieDetails: MovieDetails) {
        dao.insertMovieDetails(movieDetails)
    }
    suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return dao.getMovieDetails(movieId) ?: MovieDetails(movieId, 0, false)
    }
    suspend fun updateMovieDetails(movieDetails: MovieDetails) {
        dao.updateMovieDetails(movieDetails)
    }
}
