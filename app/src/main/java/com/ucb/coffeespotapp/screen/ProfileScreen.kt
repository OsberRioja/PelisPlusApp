package com.ucb.coffeespotapp.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ucb.network.MovieRemoteDataSource
import com.ucb.network.RetrofitBuilder
import com.ucb.coffeespotapp.ui.theme.onPrimaryLight
import com.ucb.coffeespotapp.viewModel.MovieHomeViewModel
import com.ucb.coffeespotapp.viewModel.UserStateViewModel
import com.ucb.repository.MovieRepository
import com.ucb.repository.UserRepository

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun ProfileScreen( onClickMovie: (Int) -> Unit) {
    val context = LocalContext.current
    val repository = UserRepository(context = context)
    val userStateViewModel = UserStateViewModel(repository)
    val userState by userStateViewModel.state.observeAsState()

    val dataSource: MovieRemoteDataSource = MovieRemoteDataSource(RetrofitBuilder)
    val repositoryMovie = MovieRepository(context)

    val moviesHomeViewModel: MovieHomeViewModel = MovieHomeViewModel(repositoryMovie, dataSource)
    val listMovies by moviesHomeViewModel.ratedMovies.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        userStateViewModel.getInfoUser()
        moviesHomeViewModel.getRatedMovies()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2D4B6E))
            .padding(16.dp),
        //.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(onPrimaryLight, shape = RoundedCornerShape(10.dp))
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = Color(0xFF2D4B6E),
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Nombre: ",
                color = onPrimaryLight,
                style = MaterialTheme.typography.bodyMedium
            )
            userState?.let {
                Text(
                    text = it.username,
                    color = onPrimaryLight,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Edad: ",
                color = onPrimaryLight,
                style = MaterialTheme.typography.bodyMedium
            )
            userState?.let {
                val age = 2024 - Integer.parseInt(it.birthDate.split("-")[2])
                Text(
                    text = age.toString(),
                    color = onPrimaryLight,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Correo electrónico: ",
                color = onPrimaryLight,
                style = MaterialTheme.typography.bodyMedium
            )
            userState?.let {
                Text(
                    text = it.email,
                    color = onPrimaryLight,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Tus películas reseñadas",
            color = onPrimaryLight,
            style = MaterialTheme.typography.headlineMedium
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            items(listMovies.size) { index ->
                MovieCard(
                    onClick = onClickMovie,
                    movieId = listMovies[index].movieId,
                    title = listMovies[index].title,
                    rating = listMovies[index].voteAverage,
                    imageModel = "https://image.tmdb.org/t/p/w185/${listMovies[index].posterPath}",
                )
            }
        }
    }
}