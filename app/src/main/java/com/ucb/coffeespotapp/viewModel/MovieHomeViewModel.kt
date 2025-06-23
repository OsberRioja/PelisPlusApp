package com.ucb.coffeespotapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.model.Movie
import com.ucb.model.MovieGenreCrossRef
import com.ucb.network.MovieRemoteDataSource
import com.ucb.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieHomeViewModel(
    private val repository: MovieRepository,
    private val dataSource: MovieRemoteDataSource
) : ViewModel()  {

    val isDataLoaded: Boolean = true
    val movies: LiveData<List<Movie>> = repository.getAllMovies()
    val favoriteMovies: LiveData<List<Movie>> = repository.getFavoriteMovies()
    val ratedMovies: LiveData<List<Movie>> = repository.getRatedMovies()
    val movie: LiveData<Movie>
        get() = _movie
    private val _movie = MutableLiveData<Movie>()

    init {
        fetchMoviesIfNecessary()
    }

    private fun fetchMoviesIfNecessary() {
        viewModelScope.launch {
            if (movies.value.isNullOrEmpty()) {
                fetchMovies()
            }
        }
    }

    private suspend fun fetchMovies() {
        try {
            val page1 = dataSource.getListResponse()
            val page2 = dataSource.getListResponsePartTwo()
            val page3 = dataSource.getListResponsePartThree()
            val movies = page1.results + page2.results + page3.results

            repository.insert(movies.map {it.toMovie()})
            movies.forEach {
                    movie -> movie.genre_ids.forEach {
                    genreId -> repository.insertMovieGenreCrossRef(
                listOf(
                    MovieGenreCrossRef(movie.id.toInt(), genreId)
                ))
            }
            }
        }catch (e: Exception) {
            Log.e("Loading", "Error al cargar peliculas")
        }
    }

    fun getMovieById(movieId: Int): LiveData<Movie> {
        return repository.getMovieById(movieId)
    }

    suspend fun getMoviesFilteredByGenre(genreId: Int) = repository.getMoviesFilteredByGenre(genreId)

    fun saveMovies(movies: List<Movie>) {
        viewModelScope.launch {
            repository.insert(movies)
        }
    }

    fun getMoviesByGenre(genreId: Int) {
        viewModelScope.launch {
            repository.getMoviesFilteredByGenre(genreId)
        }
    }

    fun getAllMovies() {
        viewModelScope.launch {
            repository.getAllMovies()
        }
    }
    fun getFavoriteMovies() {
        viewModelScope.launch {
            repository.getFavoriteMovies()
        }
    }

    fun getRatedMovies() {
        viewModelScope.launch {
            repository.getRatedMovies()
        }
    }
}