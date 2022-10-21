package dev.orlov.weather.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val lat: Double,
    val lon: Double,
    val isMain: Boolean = false
)
