package dev.orlov.weather.data.network.model

data class ForecastDto(
    val forecastday: List<ForecastdayDto>
)