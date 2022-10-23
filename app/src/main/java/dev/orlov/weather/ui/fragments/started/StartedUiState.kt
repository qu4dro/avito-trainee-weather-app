package dev.orlov.weather.ui.fragments.started

import dev.orlov.weather.domain.model.City
import dev.orlov.weather.utils.LoadState

data class StartedUiState(
    val isPermissionsGranted: Boolean = false,
    val isGpsEnabled: Boolean = false,
    val isCitySelected: Boolean = false,
    val loadState: LoadState? = null,
    val cities: List<City> = listOf(),
)