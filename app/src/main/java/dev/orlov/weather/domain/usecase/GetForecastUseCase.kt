package dev.orlov.weather.domain.usecase

import dev.orlov.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(query: String) = weatherRepository.getForecast(query)

}