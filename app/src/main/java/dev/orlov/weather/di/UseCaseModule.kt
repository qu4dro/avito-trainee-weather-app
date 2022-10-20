package dev.orlov.weather.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.orlov.weather.domain.repository.WeatherRepository
import dev.orlov.weather.domain.usecase.GetForecastUseCase
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
}