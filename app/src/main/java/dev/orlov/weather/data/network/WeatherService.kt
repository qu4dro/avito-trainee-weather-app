package dev.orlov.weather.data.network

import dev.orlov.weather.BuildConfig
import dev.orlov.weather.data.network.model.GetForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") q: String,
        @Query("key") api_key: String = BuildConfig.API_KEY,
    ): GetForecastResponse

}