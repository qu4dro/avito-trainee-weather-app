package dev.orlov.weather.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.orlov.weather.data.db.entity.CityEntity

@Dao
interface CitiesDao {

    @Query("SELECT * FROM cities ORDER BY name")
    fun getCities(): LiveData<List<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(city: CityEntity)

    @Delete
    suspend fun deleteCity(city: CityEntity)

    @Query("SELECT * FROM cities ORDER BY isMain LIMIT 1")
    fun getMainCity(): LiveData<CityEntity>

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