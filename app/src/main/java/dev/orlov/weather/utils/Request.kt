package dev.orlov.weather.utils

sealed class Request<T> {
    class Loading<T> : Request<T>()
    data class Success<T>(internal val data: T) : Request<T>()
    data class Error<T>(internal val error: Throwable?) : Request<T>()
}