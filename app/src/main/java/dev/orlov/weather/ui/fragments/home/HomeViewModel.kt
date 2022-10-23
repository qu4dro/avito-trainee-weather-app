package dev.orlov.weather.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.orlov.weather.domain.model.City
import dev.orlov.weather.domain.usecase.CityUseCases
import dev.orlov.weather.domain.usecase.WeatherUseCases
import dev.orlov.weather.utils.LoadState
import dev.orlov.weather.utils.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherUseCases: WeatherUseCases,
    private val cityUseCases: CityUseCases
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState
        get() = _uiState.asStateFlow()

    private var getWeatherJob: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            cityUseCases.getCities.invoke().collect { cities ->
                _uiState.update { it.copy(savedCities = cities) }
            }
        }
    }

    fun getForecast() {
        getWeatherJob?.cancel()
        getWeatherJob = viewModelScope.launch(Dispatchers.IO) {
            weatherUseCases.getForecast("Omsk").collect { request ->
                when (request) {
                    is Request.Error -> _uiState.update { it.copy(loadState = LoadState.ERROR) }
                    is Request.Loading -> _uiState.update { it.copy(loadState = LoadState.LOADING) }
                    is Request.Success -> {
                        val data = request.data
                        _uiState.update {
                            it.copy(
                                loadState = LoadState.SUCCESS,
                                weather = data
                            )
                        }
                    }
                }
            }
        }
    }
}
