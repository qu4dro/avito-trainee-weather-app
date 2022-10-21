package dev.orlov.weather.domain.usecase

import dev.orlov.weather.domain.repository.CityRepository
import javax.inject.Inject

class SearchCityUseCase @Inject constructor(private val cityRepository: CityRepository) {

    suspend operator fun invoke(query: String) = cityRepository.searchCity(query)

}