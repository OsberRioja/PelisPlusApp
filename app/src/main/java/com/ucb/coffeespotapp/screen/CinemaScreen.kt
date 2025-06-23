package com.ucb.coffeespotapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.ucb.model.Cinema
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import com.ucb.coffeespotapp.ui.theme.primaryContainerLightMediumContrast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CinemaScreen(cinemaList: List<Cinema>, onCinemaClick: (Int) -> Unit, onBackPressed: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Cines", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(primaryContainerLightMediumContrast),
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            modifier = Modifier.size(34.dp),
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF0B132B)) // Fondo azul oscuro
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Cines",
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn {
                    items(cinemaList) { cinema ->
                        CinemaItem(cinema, onCinemaClick = { onCinemaClick(cinema.cinemaId) })
                    }
                }
            }
        }
    )
}

@Composable
fun CinemaItem(cinema: Cinema, onCinemaClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFF1C2541), shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen del cine
        Image(
            painter = rememberImagePainter(cinema.imageUrl),
            contentDescription = cinema.name,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Nombre del cine
        Text(
            text = cinema.name,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )

        // Botón "VER"
        Button(
            onClick = { onCinemaClick(cinema.cinemaId) },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3A506B),
                contentColor = Color.White
            )
        ) {
            Text(text = "VER")
        }
    }
}