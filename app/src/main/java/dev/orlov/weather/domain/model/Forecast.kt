package dev.orlov.weather.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Forecast(
    val date: String,
    val temp: Double,
    val condition: Condition,
    val maxTemp: Double,
    val minTemp: Double,
    val sunrise: String,
    val sunset: String,
    val hourly: List<Hour>
): Parcelable