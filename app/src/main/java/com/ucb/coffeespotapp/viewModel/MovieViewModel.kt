package com.ucb.coffeespotapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(
    private val repository: MovieRepository
): ViewModel() {
    val star : LiveData<Int>
        get() = _star
    private val _star = MutableLiveData<Int>()

    val icon_favorite : LiveData<Boolean>
        get() = _icon_favorite
    private val _icon_favorite = MutableLiveData<Boolean>()

    fun update(newRating: Int, movieID: Int) {
        _star.value = newRating
        viewModelScope.launch(Dispatchers.Main){
            repository.updateVoteAverage(newRating, movieID)
        }
    }


}