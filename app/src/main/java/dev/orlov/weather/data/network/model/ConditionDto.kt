package dev.orlov.weather.data.network.model

data class ConditionDto(
    val code: Int,
    val icon: String,
    val text: String
)