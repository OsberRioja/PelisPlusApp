package com.ucb.repository

import com.ucb.model.Cinema

class CinemaRepository {
    private val cinemaList = listOf(
        Cinema(
            cinemaId = 1,
            name = "Cine Center",
            imageUrl = "https://www.cinecenter.com.bo/images/icons/logo.png",
            latitude = -17.4200773,
            longitude = -66.3091207,
            openingHours = "10:00 AM",
            closingHours = "11:00 PM",
            phoneNumber = "123-456-789",
            rating = 4.5f
        ),
        Cinema(
            cinemaId = 2,
            name = "Prime Cinemas",
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFRG4dwO8P3P8ZmPdd5nP1wKfIfxQK_fC19A&s",
            latitude = -17.3747688,
            longitude = -66.152916,
            openingHours = "11:00 AM",
            closingHours = "12:00 AM",
            phoneNumber = "987-654-321",
            rating = 4.8f
        ),
        Cinema(
            cinemaId = 3,
            name = "Sky Box Cinemas",
            imageUrl = "https://www.boliviaentusmanos.com/amarillas/blogos/sky-box-cinemas-logo.jpg",
            latitude = -17.3915985,
            longitude = -66.1570461,
            openingHours = "12:00 PM",
            closingHours = "10:00 PM",
            phoneNumber = "555-666-777",
            rating = 4.7f
        )
    )

    fun getCinemas(): List<Cinema> = cinemaList

    fun getCinemaById(cinemaId: Int): Cinema? = cinemaList.find { it.cinemaId == cinemaId }
}