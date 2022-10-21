package dev.orlov.weather.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.orlov.weather.domain.usecase.WeatherUseCases
import dev.orlov.weather.utils.LoadState
import dev.orlov.weather.utils.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val weatherUseCases: WeatherUseCases) :
    ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState
        get() = _uiState.asStateFlow()

    private var getWeatherJob: Job? = null

    fun getForecast() {
        getWeatherJob?.cancel()
        getWeatherJob = viewModelScope.launch(Dispatchers.IO) {
            weatherUseCases.getForecast("Irkutsk").collect { request ->
                when (request) {
                    is Request.Error -> _uiState.update { it.copy(loadState = LoadState.ERROR) }
                    is Request.Loading -> _uiState.update { it.copy(loadState = LoadState.LOADING) }
                    is Request.Success -> {
                        val data = request.data
                        _uiState.update { it.copy(loadState = LoadState.SUCCESS, weather = data ) }
                    }
                }

            }
        }
    }

}