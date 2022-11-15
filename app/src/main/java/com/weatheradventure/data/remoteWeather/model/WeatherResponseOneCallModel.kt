package com.weatheradventure.data.remoteWeather.model

import com.google.gson.annotations.SerializedName
import com.weatheradventure.Constants

data class WeatherResponseOneCallModel(
    @SerializedName(Constants.LAT)
    val latitude: Double,
    @SerializedName(Constants.LON)
    val longitude: Double,
    @SerializedName(Constants.CURRENT)
    val current: CurrentWeatherModel,
    @SerializedName(Constants.HOURLY)
    val hourly: List<CurrentWeatherModel>,
    @SerializedName(Constants.DAILY)
    val daily: List<WeatherResponseDailyModel>
)