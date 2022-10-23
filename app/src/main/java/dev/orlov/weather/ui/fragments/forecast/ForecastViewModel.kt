package dev.orlov.weather.ui.fragments.forecast

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.orlov.weather.domain.model.Forecast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ForecastUiState())
    val uiState
        get() = _uiState.asStateFlow()

    fun updateForecastList(forecast: List<Forecast>) {
        _uiState.update { it.copy(forecast = forecast) }
    }
}