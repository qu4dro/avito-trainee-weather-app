package dev.orlov.weather.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.orlov.weather.data.db.entity.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao {

    @Query("SELECT * FROM cities ORDER BY name")
    fun getCities(): Flow<List<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: CityEntity)

    @Delete
    suspend fun deleteCity(city: CityEntity)

    @Query("UPDATE cities SET isMain = :isMain WHERE id = :id")
    fun updateIsMain(isMain: Boolean, id: Int)

    @Transaction
    suspend fun updateMainCity(
        oldCity: CityEntity,
        newCity: CityEntity
    ) {
        updateIsMain(false, oldCity.id)
        updateIsMain(true, newCity.id)
    }

}