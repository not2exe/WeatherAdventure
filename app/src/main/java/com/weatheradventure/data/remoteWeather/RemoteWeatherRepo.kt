package com.weatheradventure.data.remoteWeather

import com.weatheradventure.data.remoteWeather.model.WeatherResponseOneCallModel
import kotlinx.coroutines.flow.MutableStateFlow

interface RemoteWeatherRepo {
    val weatherModel: MutableStateFlow<WeatherResponseOneCallModel?>
//    val stateOfResponse: MutableStateFlow<StateOfResponse>
    suspend fun get()
}