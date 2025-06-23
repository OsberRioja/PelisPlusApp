package com.ucb.coffeespotapp.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ucb.coffeespotapp.screen.BottomNavigationBar
import com.ucb.coffeespotapp.screen.CinemaDetailScreen
import com.ucb.coffeespotapp.screen.CinemaScreen
import com.ucb.coffeespotapp.screen.FavoritesScreen
import com.ucb.coffeespotapp.screen.HomeScreen
import com.ucb.coffeespotapp.screen.LoginScreen
import com.ucb.coffeespotapp.screen.MovieDetailScreen
import com.ucb.coffeespotapp.screen.ProfileScreen
import com.ucb.coffeespotapp.screen.RegisterScreen
import com.ucb.coffeespotapp.screen.SearchScreen
import com.ucb.coffeespotapp.screen.WelcomeScreen
import com.ucb.coffeespotapp.viewModel.CinemaViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in listOf(Screens.HomeScreen.route,
        "home", "search","favorites", "profile")

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.WelcomeScreen.route,
            modifier = if (showBottomBar) Modifier.padding(innerPadding) else Modifier
        ) {
            composable(Screens.WelcomeScreen.route) {
                WelcomeScreen(
                    onClick = {
                        navController.navigate(Screens.RegisterScreen.route)
                    },
                    onLoginClick = {
                        navController.navigate(Screens.LoginScreen.route)
                    },
                    onSkip = {
                        navController.navigate(Screens.HomeScreen.route)
                    }
                )
            }
            composable(Screens.RegisterScreen.route) {
                RegisterScreen(
                    onClick = {
                        navController.navigate(Screens.LoginScreen.route)
                    }
                )
            }
            composable(Screens.LoginScreen.route) {
                LoginScreen(
                    onClick = {
                        navController.navigate(Screens.HomeScreen.route)
                    }
                )
            }
            composable(Screens.HomeScreen.route) {
                HomeScreen(onClick = { movieId ->
                    Log.e("Navigation", "Navigating to MovieDetailScreen with movieId: $movieId")
                    navController.navigate("movie_detail_screen/$movieId")
                },
                    onNavigateToCinemas = { // Nuevo callback para navegar a la pantalla de Cines
                        navController.navigate(Screens.Cines.route)
                    }
                )
            }
            composable(
                route = "movie_detail_screen/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
                MovieDetailScreen(
                    movieId = movieId,
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }

            composable("home") {
                HomeScreen(
                    onClick = { movieId ->
                        Log.e("Navigation", "Navigating to MovieDetailScreen with movieId: $movieId")
                        navController.navigate("movie_detail_screen/$movieId")
                    },
                    onNavigateToCinemas = { // Nuevo callback para navegar a la pantalla de Cines
                        navController.navigate(Screens.Cines.route)
                    }
                )
            }
            composable("favorites") {
                FavoritesScreen(
                    onClick = { movieId ->
                        Log.e("Navigation", "Navigating to MovieDetailScreen with movieId: $movieId")
                        navController.navigate("movie_detail_screen/$movieId")
                    },
                    onNavigateToCinemas = { // Nuevo callback para navegar a la pantalla de Cines
                        navController.navigate(Screens.Cines.route)
                    }
                )
            }
            composable("profile") {
                ProfileScreen(
                    onClickMovie = { movieId ->
                        Log.e("Navigation", "Navigating to MovieDetailScreen with movieId: $movieId")
                        navController.navigate("movie_detail_screen/$movieId")
                    }
                )
            }

            composable(Screens.Cines.route) {
                val viewModel = CinemaViewModel()
                CinemaScreen(
                    cinemaList = viewModel.cinemaList,
                    onCinemaClick = { cinemaId ->
                        Log.e("CinemaScreen", "Clicked on cinemaId: $cinemaId")
                        navController.navigate("cinema_detail/$cinemaId") // Navega a la pantalla de detalles
                    },
                    onBackPressed = {
                        navController.popBackStack() // Regresa a la pantalla anterior
                    }
                )
            }

            composable("cinema_detail/{cinemaId}") { backStackEntry ->
                val cinemaId = backStackEntry.arguments?.getString("cinemaId")?.toIntOrNull()

                if (cinemaId != null) { // Verifica que cinemaId no sea nulo
                    val viewModel = CinemaViewModel() // Usa el ViewModel para obtener los datos
                    val cinema = viewModel.getCinemaById(cinemaId)

                    cinema?.let {
                        CinemaDetailScreen(
                            cinema = it,
                            onBackPressed = {
                                navController.popBackStack() // Regresa a la pantalla anterior
                            }
                        )
                    }
                } else {
                    Log.e("CinemaDetail", "Invalid cinemaId")
                    navController.popBackStack() // Regresa a la pantalla anterior si no hay un ID válido
                }
            }
            composable("search") {
                SearchScreen(onMovieClick = { movieId ->
                    Log.e("Navigation", "Navigating to MovieSearchScreen")
                    navController.navigate("movie_detail_screen/$movieId")
                });
            }
        }
    }
}