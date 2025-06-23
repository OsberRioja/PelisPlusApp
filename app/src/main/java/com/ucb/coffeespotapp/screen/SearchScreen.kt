package com.ucb.coffeespotapp.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ucb.model.Movie
import com.ucb.network.MovieRemoteDataSource
import com.ucb.network.RetrofitBuilder
import com.ucb.coffeespotapp.viewModel.MovieHomeViewModel
import com.ucb.coffeespotapp.viewModel.MovieInterestViewModel
import com.ucb.repository.MovieRepository

@Composable
fun SearchScreen(onMovieClick: (Int) -> Unit) {
    val dataSource: MovieRemoteDataSource = MovieRemoteDataSource(RetrofitBuilder)
    val context = LocalContext.current
    val repository = MovieRepository(context)

    val moviesHomeViewModel: MovieHomeViewModel = MovieHomeViewModel(repository, dataSource)
    val listMovies by moviesHomeViewModel.movies.observeAsState(emptyList())

    var filteredMovies by remember { mutableStateOf(listOf<Movie>()) }

    fun onSearch(query: String) {
        filteredMovies = if (query.isEmpty()) {
            listMovies
        } else {
            listMovies.filter { it.title.contains(query, ignoreCase = true) }
        }
    }

    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Search Screen",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                MovieSearchBar(onSearch = { query -> onSearch(query) })

                Spacer(modifier = Modifier.height(16.dp))

                if (filteredMovies.isEmpty()) {
                    Text(
                        text = "No movies found",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredMovies.chunked(2)) { rowMovies ->
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                rowMovies.forEach { movie ->
                                    MovieCard(
                                        onClick = onMovieClick,
                                        movieId = movie.movieId,
                                        title = movie.title,
                                        rating = movie.voteAverage,
                                        imageModel = "https://image.tmdb.org/t/p/w185/${movie.posterPath}",
                                    )
                                }
                                if (rowMovies.size < 2) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }

                }
            }
        }
    )
}

@Composable
fun MovieSearchBar(onSearch: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    OutlinedTextField(
        value = query,
        onValueChange = { query = it; onSearch(query) },
        label = { Text("Buscar película...") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true
    )
}