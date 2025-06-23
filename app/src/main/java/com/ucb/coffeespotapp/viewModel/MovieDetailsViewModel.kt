package com.ucb.coffeespotapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.model.MovieDetails
import com.ucb.repository.MovieDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class MovieDetailsUiState(
    val iconSelect: Boolean = false,
    val rating: Int = 0
)
class MovieDetailsViewModel(private val repository: MovieDetailsRepository) : ViewModel() {

    private val _movieDetails = MutableStateFlow(MovieDetailsUiState())
    val movieDetails: StateFlow<MovieDetailsUiState> = _movieDetails

    fun getMovieDetails(movieId: Int){
        viewModelScope.launch {
            _movieDetails.value = MovieDetailsUiState(
                iconSelect = repository.getMovieDetails(movieId).isFavorite,
                rating = repository.getMovieDetails(movieId).vote
            )
        }
        //return _movieDetails.value!!
    }

    fun saveMovieDetails(movieId: Int, vote: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            val movieDetails = MovieDetails(movieId, vote, isFavorite)
            repository.insertMovieDetails(movieDetails)
            _movieDetails.value = MovieDetailsUiState(
                iconSelect = movieDetails.isFavorite,
                rating = movieDetails.vote
            )
        }
    }

    fun updateMovieDetails(movieId: Int, vote: Int, isFavorite: Boolean) {
        val movieDetails = MovieDetails(movieId, vote, isFavorite)
        //_movieDetails.value = movieDetails
        viewModelScope.launch {
            repository.updateMovieDetails(movieDetails)
        }
    }

    fun fetchMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movieData = repository.getMovieDetails(movieId)
            Log.d("FetchMovie", "Fetched: $movieData")
            withContext(Dispatchers.Main) {
                _movieDetails.value = MovieDetailsUiState(
                    iconSelect = movieData.isFavorite,
                    rating = movieData.vote
                )
            }
        }
    }

    fun fetchMovie2(movieId: Int) {
        viewModelScope.launch {
            try {
                val movieData = repository.getMovieDetails(movieId)
                _movieDetails.value = MovieDetailsUiState(
                    iconSelect = movieData.isFavorite,
                    rating = movieData.vote
                )
            } catch (e: Exception) {
                // Manejar errores si Room falla
                Log.e("MovieDetailsViewModel", "Error fetching movie details: ${e.message}")
            }
        }
    }

}
