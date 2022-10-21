package dev.orlov.weather.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val lat: Double,
    val lon: Double,
    val isMain: Boolean = false
)
