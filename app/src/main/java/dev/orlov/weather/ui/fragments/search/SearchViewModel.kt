package dev.orlov.weather.ui.fragments.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.orlov.weather.domain.usecase.CityUseCases
import dev.orlov.weather.ui.fragments.home.HomeUiState
import dev.orlov.weather.utils.LoadState
import dev.orlov.weather.utils.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val cityUseCases: CityUseCases) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState
        get() = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun searchCity(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(800L) // Delay for text input
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


}