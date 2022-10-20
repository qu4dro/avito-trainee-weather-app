package dev.orlov.weather.domain.model

data class Hour(
    val temp: Double,
    val time: String,
    val condition: Condition
)
