package com.ucb.coffeespotapp.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.data.UserStateDao
import com.ucb.model.Movie
import com.ucb.repository.MovieRepository
import com.ucb.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieInterestViewModel: ViewModel()  {
    sealed class MoviesInterestState {
        object Loading : MoviesInterestState()
        class Success(val movies: List<Pair<String, List<Movie>>>) : MoviesInterestState()
        class Error(val message: String) : MoviesInterestState()
    }

    val _moviesInterest = MutableLiveData<MoviesInterestState>()
    val moviesInterest: LiveData<MoviesInterestState>
        get() = _moviesInterest

    fun getMovieById(context: Context, movieId: Int): LiveData<Movie> {
        return MovieRepository(context = context).getMovieById(movieId)
    }

    fun getMoviesFilteredByGenre(context: Context, isDataLoaded : Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val repository = MovieRepository(context)
                val userFromRepo = UserRepository(context).getLoginState()
                Log.d("holitaaaaaa1", userFromRepo.toString())
                val genresAll = repository.getAllGenresProfile(userFromRepo)
                val moviesFiltered = genresAll.map { genre ->
                    val movies = repository.getMoviesFilteredByGenre(genre.genreId).first()
                    Log.d("holita2.1", "Peliculas filtradas: ${genre.genreId}")
                    Log.d("holita2.2", "Peliculas filtradas: ${movies}")
                    genre.name to movies
                }
                Log.d("holita2", "Peliculas filtradas: $moviesFiltered")
                withContext(Dispatchers.Main) {
                    Log.d("holita10", "ahora no: ${_moviesInterest.value}")
                    _moviesInterest.value = MoviesInterestState.Success(moviesFiltered as List<Pair<String, List<Movie>>>)
                    Log.d("holita10", "ahora si: ${_moviesInterest.value}")
                }
            } catch (e: Exception) {
                Log.e("MovieHomeViewModel", "No se han cargado las peliculas")
                withContext(Dispatchers.Main) {
                    _moviesInterest.value = MoviesInterestState.Error("No se han cargado las peliculas")
                }
            }
        }
    }
}