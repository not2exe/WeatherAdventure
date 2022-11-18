package com.weatheradventure.data.remoteWeather.model

import com.google.gson.annotations.SerializedName
import com.weatheradventure.Constants

data class CurrentWeatherResponseModel(
    @SerializedName(Constants.DATE)
    val date: Long,
    @SerializedName(Constants.TEMP)
    val temp: Double,
    @SerializedName(Constants.FEELS_LIKE)
    val feelsLike: Double,
    @SerializedName(Constants.HUMIDITY)
    val humidity: Int,
    @SerializedName(Constants.UVI)
    val uvi: Double,
    @SerializedName(Constants.WIND_SPEED)
    val windSpeed: Double,
    @SerializedName(Constants.WEATHER)
    val weatherDescription: List<WeatherResponseDescription>
)
