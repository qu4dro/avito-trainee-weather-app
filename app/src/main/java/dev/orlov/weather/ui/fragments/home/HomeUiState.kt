package dev.orlov.weather.ui.fragments.home

import dev.orlov.weather.domain.model.Weather
import dev.orlov.weather.utils.LoadState

data class HomeUiState(
    val isLocationSelected: Boolean = false,
    val loadState: LoadState = LoadState.SUCCESS,
    val weather: Weather? = null
)