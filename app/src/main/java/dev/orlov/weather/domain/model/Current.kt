package dev.orlov.weather.domain.model

data class Current(
    val temp: Double,
    val feelsLike: Double,
    val condition: Condition,
)
