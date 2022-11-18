package com.weatheradventure.data.remoteLocations

import com.google.gson.annotations.SerializedName
import com.weatheradventure.Constants

data class OnePlaceModel(
    @SerializedName(Constants.FORMATTED)
    val formatted: String,
    @SerializedName(Constants.LAT)
    val lat: Double,
    @SerializedName(Constants.LON)
    val lon: Double
)
