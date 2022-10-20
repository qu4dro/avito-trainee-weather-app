package dev.orlov.weather.data.network.model

data class GetForecastResponse(
    val current: CurrentDto,
    val forecast: ForecastDto,
    val location: LocationDto
)