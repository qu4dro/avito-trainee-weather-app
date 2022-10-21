package dev.orlov.weather.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.orlov.weather.domain.repository.CityRepository
import dev.orlov.weather.domain.repository.WeatherRepository
import dev.orlov.weather.domain.usecase.CityUseCases
import dev.orlov.weather.domain.usecase.GetForecastUseCase
import dev.orlov.weather.domain.usecase.SearchCityUseCase
import dev.orlov.weather.domain.usecase.WeatherUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetForecastUseCase(weatherRepository: WeatherRepository) = GetForecastUseCase(weatherRepository)

    @Singleton
    @Provides
    fun provideWeatherUseCases(getForecast: GetForecastUseCase) = WeatherUseCases(getForecast)

    @Singleton
    @Provides
    fun provideSearchCityUseCase(cityRepository: CityRepository) = SearchCityUseCase(cityRepository)

    @Singleton
    @Provides
    fun provideCityUseCases(searchCity: SearchCityUseCase) = CityUseCases(searchCity)
}