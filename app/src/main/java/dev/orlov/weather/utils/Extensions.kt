package dev.orlov.weather.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

private const val INPUT_FORMAT_DAY = "yyyy-MM-dd"
private const val INPUT_FORMAT_DAY_TIME = "yyyy-MM-dd HH:mm"

fun String.formatTime(): String {
    val outputFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    val inputFormat: DateFormat = SimpleDateFormat(INPUT_FORMAT_DAY_TIME, Locale.ENGLISH)
    return outputFormat.format(inputFormat.parse(this))
}

fun String.formatDayWeek(): String {
    val outputFormat: DateFormat = SimpleDateFormat("EEEE, MMM dd", Locale.ENGLISH)
    val inputFormat: DateFormat = SimpleDateFormat(INPUT_FORMAT_DAY, Locale.ENGLISH)
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