package dev.orlov.weather.domain.usecase

import dev.orlov.weather.domain.model.City
import dev.orlov.weather.domain.repository.CityRepository
import javax.inject.Inject

class InsertCityUseCase @Inject constructor(private val repository: CityRepository) {

    suspend operator fun invoke(city: City) = repository.insertCity(city)

}