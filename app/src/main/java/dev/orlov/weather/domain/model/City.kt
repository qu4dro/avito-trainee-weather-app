package dev.orlov.weather.domain.model

data class City(
    val id: Int,
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val isMain: Boolean
)