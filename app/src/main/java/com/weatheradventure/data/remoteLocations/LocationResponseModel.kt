package com.weatheradventure.data.remoteLocations

import com.google.gson.annotations.SerializedName
import com.weatheradventure.Constants

data class LocationResponseModel(
    @SerializedName(Constants.FEATURES)
    val features: List<PropertiesModel>
)