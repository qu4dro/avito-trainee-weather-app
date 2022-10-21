package dev.orlov.weather.domain.repository

import androidx.lifecycle.LiveData
import dev.orlov.weather.domain.model.City

interface CityRepository {

    fun getMainCity(): LiveData<City>
    fun getCities(): LiveData<List<City>>

    suspend fun updateMainCity(
        oldCity: City,
        newCity: City
    )

    suspend fun insertCity(city: City)
    suspend fun deleteCity(city: City)


}