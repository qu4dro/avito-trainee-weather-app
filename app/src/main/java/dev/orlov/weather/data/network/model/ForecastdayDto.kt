package dev.orlov.weather.data.network.model

data class ForecastdayDto(
    val astro: Astro,
    val date: String,
    val date_epoch: Int,
    val day: Day,
    val hour: List<HourDto>
)