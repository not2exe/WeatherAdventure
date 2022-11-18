package com.weatheradventure.domain.model

data class DailyWeatherModel(
    val date: Long,
    val place: Place,
    val degreesNight: Int,
    val degreesDay: Int,
    val humidity: Int,
    val uvi: UVI,
    val windSpeed: Int,
    val description: String,
    val sunset: Long,
    val sunrise: Long,
    val dayIconId: Int?,
    val nightIconId: Int?,
    val currentWeatherIconId: Int? = null,
    val degrees: Int? = null,
    val feelsLike: Int? = null,
)
