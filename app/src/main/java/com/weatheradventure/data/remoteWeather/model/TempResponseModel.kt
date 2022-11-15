package com.weatheradventure.data.remoteWeather.model

import com.google.gson.annotations.SerializedName
import com.weatheradventure.Constants

data class TempResponseModel(
    @SerializedName(Constants.DAY)
    val day: Double,
    @SerializedName(Constants.NIGHT)
    val night: Double
)
