package dev.orlov.weather.domain.usecase

import dev.orlov.weather.domain.repository.CityRepository
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(private val repository: CityRepository) {

    suspend operator fun invoke() = repository.getCities()

}