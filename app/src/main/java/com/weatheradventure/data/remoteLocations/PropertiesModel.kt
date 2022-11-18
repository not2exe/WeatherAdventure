package com.weatheradventure.data.remoteLocations

import com.google.gson.annotations.SerializedName
import com.weatheradventure.Constants

data class PropertiesModel(
    @SerializedName(Constants.PROPERTIES)
    val properties: OnePlaceModel
)
