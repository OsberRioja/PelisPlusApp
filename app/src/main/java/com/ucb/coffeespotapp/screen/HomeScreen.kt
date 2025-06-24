package com.ucb.coffeespotapp.screen

import android.annotation.SuppressLint
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ucb.model.Movie
import com.ucb.network.MovieRemoteDataSource
import com.ucb.network.RetrofitBuilder
import com.ucb.coffeespotapp.viewModel.MovieHomeViewModel
import com.ucb.coffeespotapp.R
import com.ucb.coffeespotapp.ui.theme.onPrimaryDark
import com.ucb.coffeespotapp.ui.theme.onPrimaryLight
import com.ucb.coffeespotapp.ui.theme.primaryContainerLightMediumContrast
import com.ucb.coffeespotapp.ui.theme.tertiaryCommon
import com.ucb.coffeespotapp.viewModel.MovieInterestViewModel
import com.ucb.repository.MovieRepository

@Composable
fun HomeScreen(onClick: (Int) -> Unit, onNavigateToCinemas: () -> Unit) {
    Scaffold(
        content = {paddingValues ->
            MovieScreen(
                modifier = Modifier.padding(paddingValues),
                onClickMovie = onClick,
                onNavigateToCinemas = onNavigateToCinemas
            )
        }
    )
}

@Composable
fun MovieCard(onClick: (Int) -> Unit, movieId: Int, title: String,rating: Double, imageModel: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(8.dp)
            .size(width = 170.dp, height = 235.dp)
            .clickable {
                Log.d(
                    "MovieCard",
                    "Navigating with movieId: $movieId"
                ) // Log para verificar idMovie
                onClick(movieId)
            },
    ) {
        Column(
            modifier = Modifier
                .background(onPrimaryDark)
        ) {
            AsyncImage(
                model = imageModel,
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(180.dp)
                    .width(160.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
            )
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = onPrimaryLight,
                    modifier = Modifier
                        .widthIn(max = 130.dp)
                        .padding(8.dp)
                        .horizontalScroll(rememberScrollState())
                )
                Text(
                    text = String.format("%.1f", rating),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = tertiaryCommon,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }

        }
    }

}

@Composable
fun MovieSection(title: String, movies: List<Movie>, onClickMovie: (Int) -> Unit) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = onPrimaryLight,
            modifier = Modifier.padding(8.dp)
        )
        LazyRow {
            items(movies.size) { it ->
                MovieCard(
                    onClick = onClickMovie,
                    movieId = movies[it].movieId,
                    title = movies[it].title,
                    rating = movies[it].voteAverage,
                    imageModel = "https://image.tmdb.org/t/p/w185/${movies[it].posterPath}"
                )
            }
        }
    }
}

@Composable
fun CinemasButton(onNavigateToCinemas: () -> Unit) { // Recibe el callback como parámetro
    OutlinedButton(
        onClick = { onNavigateToCinemas() }, // Usa el callback para navegar
        border = BorderStroke(1.dp, onPrimaryLight),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(48.dp)
    ) {
        Text(
            text = stringResource(id = R.string.cinema_label),
            color = onPrimaryLight,
            fontSize = 18.sp
        )
    }
}
@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun MovieScreen(modifier: Modifier, onClickMovie: (Int) -> Unit, onNavigateToCinemas: () -> Unit) {
    val dataSource: MovieRemoteDataSource = MovieRemoteDataSource(RetrofitBuilder)
    val context = LocalContext.current
    val repository = MovieRepository(context)

    val moviesHomeViewModel: MovieHomeViewModel = MovieHomeViewModel(repository, dataSource)
    val listMovies by moviesHomeViewModel.movies.observeAsState(emptyList())

    val movieInterestViewModel: MovieInterestViewModel = MovieInterestViewModel()
    var moviesUI by remember { mutableStateOf(listOf<Pair<String, List<Movie>>>()) }
    movieInterestViewModel.getMoviesFilteredByGenre(LocalContext.current, true)

    LaunchedEffect(Unit) {
        moviesHomeViewModel.getAllMovies()
    }

    fun updateUI(moviesInterestState: MovieInterestViewModel.MoviesInterestState) {
        when(moviesInterestState) {
            is MovieInterestViewModel.MoviesInterestState.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }
            is MovieInterestViewModel.MoviesInterestState.Error -> {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
            is MovieInterestViewModel.MoviesInterestState.Success -> {
                moviesUI = moviesInterestState.movies
            }
        }
    }
    movieInterestViewModel.moviesInterest.observe(
        LocalLifecycleOwner.current,
        Observer(::updateUI)
    )
    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(primaryContainerLightMediumContrast)
    ){
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(primaryContainerLightMediumContrast)
                .padding(top = 16.dp, bottom = 90.dp)
        ) {
            item{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    MovieSection(
                        title = stringResource(id = R.string.label_interes),
                        movies = listMovies,
                        onClickMovie = onClickMovie
                    )
                }
            }
            items(moviesUI.size) {
                if (moviesUI.isNotEmpty()) {
                    moviesUI[it].second?.let { it1 ->
                        MovieSection(
                            title = moviesUI[it].first,
                            movies = it1,
                            onClickMovie = onClickMovie
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .zIndex(10F),
            contentAlignment = Alignment.BottomCenter
        ){
            CinemasButton(onNavigateToCinemas = onNavigateToCinemas)
        }
    }

}
