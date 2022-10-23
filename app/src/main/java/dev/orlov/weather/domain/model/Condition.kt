package dev.orlov.weather.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Condition(
    val text: String,
    val icon_url: String
): Parcelable
