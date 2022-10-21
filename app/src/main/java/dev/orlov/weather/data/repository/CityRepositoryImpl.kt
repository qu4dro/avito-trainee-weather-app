package dev.orlov.weather.data.repository

import androidx.lifecycle.Transformations
import dev.orlov.weather.data.db.CitiesDao
import dev.orlov.weather.data.toDomain
import dev.orlov.weather.data.toEntity
import dev.orlov.weather.domain.model.City
import dev.orlov.weather.domain.repository.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(private val dao: CitiesDao) : CityRepository {

    override fun getMainCity() = Transformations.map(dao.getMainCity()) { it.toDomain() }

    override fun getCities() =
        Transformations.map(dao.getCities()) { cities -> cities.map { it.toDomain() } }

    override suspend fun updateMainCity(oldCity: City, newCity: City) = dao.updateMainCity(oldCity.toEntity(), newCity.toEntity())

    override suspend fun insertCity(city: City) = dao.insertCity(city.toEntity())

    override suspend fun deleteCity(city: City) = dao.deleteCity(city.toEntity())

}