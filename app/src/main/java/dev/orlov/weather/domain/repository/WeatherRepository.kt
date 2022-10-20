package dev.orlov.weather.domain.repository

import dev.orlov.weather.domain.model.Weather
import dev.orlov.weather.utils.Request
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getForecast(query: String): Flow<Request<Weather>>

}