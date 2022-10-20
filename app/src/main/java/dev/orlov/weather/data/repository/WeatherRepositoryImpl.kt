package dev.orlov.weather.data.repository

import dev.orlov.weather.data.network.WeatherService
import dev.orlov.weather.data.toDomain
import dev.orlov.weather.domain.model.Weather
import dev.orlov.weather.domain.repository.WeatherRepository
import dev.orlov.weather.utils.Request
import dev.orlov.weather.utils.RequestUtils.requestFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val service: WeatherService) : WeatherRepository {

    override suspend fun getForecast(query: String): Flow<Request<Weather>> {
        return requestFlow {
            val response = service.getForecast(query)
            response.toDomain()
        }
    }

}