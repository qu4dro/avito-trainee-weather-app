package dev.orlov.weather.ui.fragments.started

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.orlov.weather.domain.model.City
import dev.orlov.weather.domain.usecase.CityUseCases
import dev.orlov.weather.utils.LoadState
import dev.orlov.weather.utils.LocationLiveData
import dev.orlov.weather.utils.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartedViewModel @Inject constructor(
    application: Application,
    private val cityUseCases: CityUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(StartedUiState())
    val uiState
        get() = _uiState.asStateFlow()

    private val _locationLiveData = LocationLiveData(application)
    val locationLiveData
        get() = _locationLiveData

    private var searchJob: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            cityUseCases.getCities.invoke().collect { cities ->
                if (cities.isNotEmpty()) {
                    updateIsCitySelected(true)
                }
            }
        }
    }

    fun searchCity(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            cityUseCases.searchCity(query).collect { request ->
                when (request) {
                    is Request.Error -> {
                        _uiState.update {
                            it.copy(
                                cities = listOf(),
                                loadState = LoadState.ERROR
                            )
                        }
                    }
                    is Request.Loading -> {
                        _uiState.update {
                            it.copy(
                                cities = listOf(),
                                loadState = LoadState.LOADING
                            )
                        }
                    }
                    is Request.Success -> {
                        val data = request.data
                        _uiState.update {
                            it.copy(
                                cities = data,
                                loadState = LoadState.SUCCESS
                            )
                        }
                    }
                }
            }
        }
    }

    fun selectCity(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            cityUseCases.insertCity.invoke(city)
            updateIsCitySelected(true)
        }
    }

    fun updateIsCitySelected(isSelected: Boolean) {
        _uiState.update { it.copy(isCitySelected = isSelected) }
    }

    fun updateGpsStatus(isEnabled: Boolean) {
        _uiState.update { it.copy(isGpsEnabled = isEnabled) }
    }

    fun updatedPermissionsStatus(isGranted: Boolean) {
        _uiState.update { it.copy(isPermissionsGranted = isGranted) }
    }

}