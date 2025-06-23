package com.ucb.coffeespotapp.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.model.User
import com.ucb.model.UserGenreCrossRef
import com.ucb.repository.MovieRepository
import com.ucb.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel (
    private val repository: UserRepository,
    private val context: Context
): ViewModel() {
    val movieRepository = MovieRepository(context)
    var selectedGenres = mutableStateListOf<String>()

    fun updateSelectedGenres(newSelectedGenres: List<String>) {
        selectedGenres.clear()
        selectedGenres.addAll(newSelectedGenres)
    }

    fun saveUser(user: User) {
        movieRepository.tester()
        Log.d("holita", user.toString())
        viewModelScope.launch(Dispatchers.IO){
            val userId = repository.insert(user)
            Log.d("holitaaa", userId.toString())
            Log.d("holiyta", selectedGenres.toString())
            selectedGenres.forEach { genreName ->
                val genreId = repository.getGenreIdByName(genreName)
                Log.d("holitaa", genreId.toString())
                withContext(Dispatchers.Main) {
                    repository.insertUserGenreCrossRef(UserGenreCrossRef(userId.toInt(), genreId))
                }
            }
        }
    }

    private var _state = MutableStateFlow<LoginState>(LoginState.LoggedOut)
    val state : StateFlow<LoginState> = _state

    sealed class LoginState {
        object Loading: LoginState()
        object LoggedOut: LoginState()
        data class DoLogin(val message: String): LoginState()
        data class Error(val message: String): LoginState()
    }

    fun doLogin(userName: String, password: String) {
        _state.value = LoginState.Loading
        viewModelScope.launch {
            if( repository.loginUser(userName, password)) {
                _state.value = LoginState.DoLogin("Inicio de sesión exitoso")
                val userIdCurrent = repository.getIdByUsername(userName)
                repository.saveLoginState(userIdCurrent, true)
                Log.d("holitawww", userIdCurrent.toString())
            } else {
                _state.value = LoginState.Error("Credenciales incorrectas")
            }
        }
    }
}