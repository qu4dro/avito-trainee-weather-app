package dev.orlov.weather.data

import dev.orlov.weather.data.network.model.*
import dev.orlov.weather.domain.model.*

fun GetForecastResponse.toDomain() = Weather(
    current = current.toDomain(),
    forecast = forecast.forecastday.map { it.toDomain() }
)

fun CurrentDto.toDomain() = Current(
    temp = temp_c,
    feelsLike = feelslike_c,
    condition = condition.toDomain()
)

fun ConditionDto.toDomain() = Condition(
    text = text,
    icon_url = icon
)

fun ForecastdayDto.toDomain() = Forecast(
    date = date,
    temp = day.avgtemp_c,
    condition = day.condition.toDomain(),
    maxTemp = day.maxtemp_c,
    minTemp = day.mintemp_c,
    sunrise = astro.sunrise,
    sunset = astro.sunset,
    hourly = hour.map { it.toDomain() }
)

fun HourDto.toDomain() = Hour(
    temp = temp_c,
    time = time,
    condition = condition.toDomain()
)