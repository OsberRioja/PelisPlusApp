package com.ucb.model

data class Cinema(
    val cinemaId: Int,       // ID único del cine
    val name: String,        // Nombre del cine
    val imageUrl: String,    // URL de la imagen del cine
    val latitude: Double,    // Latitud (para mapas)
    val longitude: Double,   // Longitud (para mapas)
    val openingHours: String, // Horario de apertura
    val closingHours: String, // Horario de cierre
    val phoneNumber: String, // Teléfono de contacto
    val rating: Float        // Calificación del cine
)