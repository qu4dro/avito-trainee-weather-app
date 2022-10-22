package dev.orlov.weather.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

fun String.formatTime(): String {
    val outputFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
    return outputFormat.format(inputFormat.parse(this))
}

fun Int.toHumidityString(): String {
    return "$this%"
}

fun Double.toWindStringMps(): String {
    val mps = this.roundToInt() * 1000 / 3600
    return "$mps m/s"
}

fun Double.toCelsiusString(): String {
    return this.roundToInt().toString() + "°"
}