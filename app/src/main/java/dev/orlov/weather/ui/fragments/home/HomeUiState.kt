package dev.orlov.weather.ui.fragments.home

import dev.orlov.weather.utils.LoadState

data class HomeUiState(
    val isLocationSelected: Boolean = false,
    val loadState: LoadState = LoadState.SUCCESS
)