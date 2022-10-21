package dev.orlov.weather.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.orlov.weather.data.repository.CityRepositoryImpl
import dev.orlov.weather.data.repository.WeatherRepositoryImpl
import dev.orlov.weather.domain.repository.CityRepository
import dev.orlov.weather.domain.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    abstract fun provideCityRepository(
        cityRepositoryImpl: CityRepositoryImpl
    ): CityRepository

}