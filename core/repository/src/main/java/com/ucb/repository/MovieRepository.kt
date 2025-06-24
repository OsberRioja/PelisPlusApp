package com.ucb.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.ucb.data.AppRoomDatabase
import com.ucb.model.Genre
import com.ucb.model.Movie
import com.ucb.model.MovieGenreCrossRef
import com.ucb.model.MovieWithGenres
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieRepository(private val context: Context)  {
    private val movieDao = AppRoomDatabase.getDatabase(context).movieDao()
    val stateDao = AppRoomDatabase.getDatabase(context).stateDao()

    init {
        initializeGenres()
    }

    private fun initializeGenres() {
        CoroutineScope(Dispatchers.IO).launch {
            val genresInDb = movieDao.getAllGenres()
            if(genresInDb.isEmpty()) {
                val genres = listOf(
                    Genre(genreId = 28, name = "Accion"),
                    Genre(genreId = 16, name = "Animacion"),
                    Genre(genreId = 12, name = "Aventura"),
                    Genre(genreId = 878, name = "Ciencia Ficcion"),
                    Genre(genreId = 35, name = "Comedia"),
                    Genre(genreId = 99, name = "Documental"),
                    Genre(genreId = 18, name = "Drama"),
                    Genre(genreId = 14, name = "Fantasia"),
                    Genre(genreId = 10751, name = "Familiar-Infantil"),
                    Genre(genreId = 10402, name = "Musical"),
                    Genre(genreId = 9648, name = "Suspenso"),
                    Genre(genreId = 53, name = "Terror")
                )
                movieDao.insertGenres(genres)
            }
        }
    }
    suspend fun insert(movies: List<Movie>) {
        return movieDao.insertMovies(movies)
    }

    fun getMoviesFilteredByGenre(genreId: Int): Flow<List<Movie>?> {
        return movieDao.getGenreWithMovies(genreId)
    }

    suspend fun insertGenres(genres: List<Genre>) {
        return movieDao.insertGenres(genres)
    }

    fun getAllMovies(): LiveData<List<Movie>> {
        return movieDao.getAllMovies()
    }

    fun getFavoriteMovies(): LiveData<List<Movie>>{
        Log.d("favorite", "${movieDao.getFavoriteMovies().value}")
        return movieDao.getFavoriteMovies()
    }

    fun getRatedMovies(): LiveData<List<Movie>> {
        return movieDao.getRatedMovies()
    }
    suspend fun isMovieListEmpty(): Boolean {
        return movieDao.getMovieCount() == 0
    }

    suspend fun insertMovieGenreCrossRef(crossRef: List<MovieGenreCrossRef>) {
        return movieDao.insertMovieGenreCrossRef(crossRef)
    }

    suspend fun getMovieWithGenres(movieId: Int): List<MovieWithGenres> {
        return movieDao.getMovieWithGenres(movieId)
    }

    fun getMovieById(movieId: Int): LiveData<Movie> {
        return movieDao.getMovieById(movieId)
    }

    fun getAllGenresProfile(userId: Int): List<Genre> {
        return movieDao.getAllGenresProfile(userId)
    }

    fun updateVoteAverage( newRating: Int, movieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val movie = movieDao.getMovieById2(movieId)
            val rating = movie?.voteAverage
            Log.d("holitarating", rating.toString())
            if (rating != null) {
                //movieDao.setVoteStars(newRating, movieId)
                val calculatedRating = (rating + newRating) / 2
                Log.d(
                    "holitaDB_OPERATION",
                    "VoteStars updated: movieId=$movieId, newRating=$calculatedRating"
                )
                withContext(Dispatchers.IO) {
                    movieDao.updateVotes(movieId, newRating, calculatedRating)
                }
            }

        }
    }

    fun updateFavorite(movieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val movie = movieDao.getMovieById2(movieId)
            withContext(Dispatchers.IO) {
                movieDao.updateFavorite(movieId)
            }
        }
    }

    fun tester(): String{
        return "It's alright"
    }
}