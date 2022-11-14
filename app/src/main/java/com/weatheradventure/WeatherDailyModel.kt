package com.weatheradventure

data class WeatherDailyModel(
    val degrees: Int,
    val feelsLike: Int,
    val degreesMin: Int,
    val degreesMax: Int,
    val humidity: Int,
    val windSpeed: Int,
    val description: String,
    val sunset: Long,
    val sunrise: Long,
    val longitude: Double,
    val latitude: Double
)
