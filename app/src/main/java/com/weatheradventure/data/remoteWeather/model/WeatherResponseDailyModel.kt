package com.weatheradventure.data.remoteWeather.model

import com.google.gson.annotations.SerializedName
import com.weatheradventure.Constants

data class WeatherResponseDailyModel(
    @SerializedName(Constants.DATE)
    val date: Long,
    @SerializedName(Constants.SUNRISE)
    val sunrise: Long,
    @SerializedName(Constants.SUNSET)
    val sunset: Long,
    @SerializedName(Constants.TEMP)
    val temp: TempResponseModel,
    @SerializedName(Constants.FEELS_LIKE)
    val feelsLike: TempResponseModel,
    @SerializedName(Constants.HUMIDITY)
    val humidity: Int,
    @SerializedName(Constants.WIND_SPEED)
    val windSpeed: Double,
    @SerializedName(Constants.WEATHER)
    val weatherDescription: List<WeatherResponseDescription>,
    @SerializedName(Constants.PROBABILITY_OF_PRECIPITATION)
    val pop: Double,
    @SerializedName(Constants.UVI)
    val uvi: Double
)
