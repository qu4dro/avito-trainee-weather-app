package dev.orlov.weather.domain.repository

import dev.orlov.weather.domain.model.City
import dev.orlov.weather.utils.Request
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    fun getCities(): Flow<List<City>>

    suspend fun updateMainCity(city: City)
    suspend fun insertCity(city: City)
    suspend fun deleteCity(city: City)
    suspend fun searchCity(query: String): Flow<Request<List<City>>>


}