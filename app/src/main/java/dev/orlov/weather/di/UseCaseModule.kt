package dev.orlov.weather.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.orlov.weather.domain.repository.CityRepository
import dev.orlov.weather.domain.repository.WeatherRepository
import dev.orlov.weather.domain.usecase.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetForecastUseCase(weatherRepository: WeatherRepository) =
        GetForecastUseCase(weatherRepository)

    @Singleton
    @Provides
    fun provideWeatherUseCases(getForecast: GetForecastUseCase) = WeatherUseCases(getForecast)

    @Singleton
    @Provides
    fun provideSearchCityUseCase(cityRepository: CityRepository) = SearchCityUseCase(cityRepository)

    @Singleton
    @Provides
    fun provideGetCitiesUseCase(cityRepository: CityRepository) =
        GetCitiesUseCase(cityRepository)

    @Singleton
    @Provides
    fun provideInsertCityUseCase(cityRepository: CityRepository) = InsertCityUseCase(cityRepository)

    @Singleton
    @Provides
    fun provideUpdateSelectedCityUseCase(cityRepository: CityRepository) =
        UpdateSelectedCityUseCase(cityRepository)

    @Singleton
    @Provides
    fun provideCityUseCases(
        searchCity: SearchCityUseCase,
        getCitiesUseCase: GetCitiesUseCase,
        insertCityUseCase: InsertCityUseCase,
        updateSelectedCityUseCase: UpdateSelectedCityUseCase
    ) = CityUseCases(searchCity, getCitiesUseCase, insertCityUseCase, updateSelectedCityUseCase)
}