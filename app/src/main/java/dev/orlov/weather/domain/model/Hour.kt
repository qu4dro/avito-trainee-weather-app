package dev.orlov.weather.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hour(
    val temp: Double,
    val time: String,
    val condition: Condition
): Parcelable
