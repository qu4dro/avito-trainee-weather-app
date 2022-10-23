package dev.orlov.weather.data.repository

import dev.orlov.weather.data.db.CitiesDao
import dev.orlov.weather.data.network.WeatherService
import dev.orlov.weather.data.toDomain
import dev.orlov.weather.data.toEntity
import dev.orlov.weather.domain.model.City
import dev.orlov.weather.domain.repository.CityRepository
import dev.orlov.weather.utils.Request
import dev.orlov.weather.utils.RequestUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val dao: CitiesDao,
    private val service: WeatherService
) : CityRepository {

    override fun getCities() =
        dao.getCities().map { it.map { it.toDomain() } }

    override suspend fun updateMainCity(city: City) =
        dao.updateSelectedCity(city.toEntity().id)

    override suspend fun insertCity(city: City) = dao.insertCityAndUpdateSelected(city.toEntity())

    override suspend fun deleteCity(city: City) = dao.deleteCity(city.toEntity())

    override suspend fun searchCity(query: String): Flow<Request<List<City>>> {
        return RequestUtils.requestFlow {
            val response = service.searchCity(query)
            response.map { it.toDomain() }
        }
    }

}