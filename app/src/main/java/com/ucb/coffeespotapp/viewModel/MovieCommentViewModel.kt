package com.ucb.coffeespotapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.model.MovieComment
import com.ucb.repository.MovieCommentRepository
import kotlinx.coroutines.launch

class MovieCommentViewModel(private val repository: MovieCommentRepository) : ViewModel() {

    private val _comments = MutableLiveData<List<MovieComment>>()
    val comments: LiveData<List<MovieComment>> get() = _comments

    fun getCommentsForMovie(movieId: Int) {
        viewModelScope.launch {
            _comments.value = repository.getCommentsForMovie(movieId)
        }
    }

    fun addComment(movieId: Int, comment: String) {
        viewModelScope.launch {
            val newComment = MovieComment(movieId = movieId, comment = comment)
            repository.insertComment(newComment)
            getCommentsForMovie(movieId)
        }
    }

    fun deleteComment(comment: MovieComment) {
        viewModelScope.launch {
            repository.deleteComment(comment)
            getCommentsForMovie(comment.movieId)
        }
    }

    fun updateComment(comment: MovieComment) {
        viewModelScope.launch {
            repository.updateComment(comment)
            getCommentsForMovie(comment.movieId)
        }
    }
}
