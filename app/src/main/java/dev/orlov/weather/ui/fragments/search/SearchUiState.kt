package dev.orlov.weather.ui.fragments.search

import dev.orlov.weather.domain.model.City
import dev.orlov.weather.utils.LoadState

data class SearchUiState(
    val cities: List<City> = listOf(),
    val loadState: LoadState? = null
)