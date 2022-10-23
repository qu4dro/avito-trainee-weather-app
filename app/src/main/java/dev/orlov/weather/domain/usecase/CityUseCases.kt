package dev.orlov.weather.domain.usecase

data class CityUseCases(
    val searchCity: SearchCityUseCase,
    val getCities: GetCitiesUseCase,
    val insertCity: InsertCityUseCase,
    val updateSelectedCity: UpdateSelectedCityUseCase
)
