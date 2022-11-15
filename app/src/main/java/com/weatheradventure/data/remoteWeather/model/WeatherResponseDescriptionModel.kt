package com.weatheradventure.data.remoteWeather.model

import com.google.gson.annotations.SerializedName
import com.weatheradventure.Constants

data class WeatherResponseDescription(
    @SerializedName(Constants.ID)
    val id: String,
    @SerializedName(Constants.MAIN)
    val main: String,
    @SerializedName(Constants.ICON)
    val iconId: String
)