package dev.orlov.weather.ui.fragments.forecast

import dev.orlov.weather.domain.model.Forecast

data class ForecastUiState(
    val forecast: List<Forecast> = listOf()
)