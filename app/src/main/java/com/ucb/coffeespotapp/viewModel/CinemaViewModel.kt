package com.ucb.coffeespotapp.viewModel

import androidx.lifecycle.ViewModel
import com.ucb.model.Cinema
import com.ucb.repository.CinemaRepository

class CinemaViewModel : ViewModel() {
    private val repository = CinemaRepository()

    val cinemaList: List<Cinema> = repository.getCinemas()

    fun getCinemaById(cinemaId: Int): Cinema? {
        return repository.getCinemaById(cinemaId)
    }
}