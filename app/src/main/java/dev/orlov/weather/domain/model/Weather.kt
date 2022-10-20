package dev.orlov.weather.domain.model

data class Weather(
    val current: Current,
    val forecast: List<Forecast>
)