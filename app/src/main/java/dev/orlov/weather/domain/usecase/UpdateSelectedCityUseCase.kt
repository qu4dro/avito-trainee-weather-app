package dev.orlov.weather.domain.usecase

import dev.orlov.weather.domain.model.City
import dev.orlov.weather.domain.repository.CityRepository
import javax.inject.Inject

class UpdateSelectedCityUseCase @Inject constructor(private val cityRepository: CityRepository) {

    suspend operator fun invoke(city: City) = cityRepository.updateMainCity(city)

}