package dev.orlov.weather.ui.fragments.home

import dev.orlov.weather.domain.model.City
import dev.orlov.weather.domain.model.Weather
import dev.orlov.weather.utils.LoadState

data class HomeUiState(
    val savedCities: List<City> = listOf(),
    val loadState: LoadState = LoadState.SUCCESS,
    val weather: Weather? = null
)