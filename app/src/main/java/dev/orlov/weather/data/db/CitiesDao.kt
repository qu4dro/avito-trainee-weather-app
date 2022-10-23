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

    @Query("UPDATE cities SET isMain = 0 WHERE id != :id")
    fun setUnselected(id: Int)

    @Query("UPDATE cities SET isMain = 1 WHERE id = :id")
    fun setSelected(id: Int)

    @Transaction
    suspend fun updateSelectedCity(id: Int) {
        setUnselected(id)
        setSelected(id)
    }

    @Transaction
    suspend fun insertCityAndUpdateSelected(
        city: CityEntity
    ) {
        insertCity(city)
        setUnselected(city.id)
    }

}