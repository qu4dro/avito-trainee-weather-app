package dev.orlov.weather.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.orlov.weather.domain.model.City
import dev.orlov.weather.domain.usecase.CityUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(private val citiesUseCases: CityUseCases) : ViewModel() {

    private val _cities: Flow<List<City>> = citiesUseCases.getCities.invoke()
    val cities
        get() = _cities.asLiveData()

    fun updateSelectedCity(city: City) {
        viewModelScope.launch(Dispatchers.IO) {
            citiesUseCases.updateSelectedCity.invoke(city)
        }
    }

}