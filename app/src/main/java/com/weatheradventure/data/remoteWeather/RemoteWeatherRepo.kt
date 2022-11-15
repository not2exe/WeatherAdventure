package com.weatheradventure.data.remoteWeather

interface RemoteWeatherRepo {
    suspend fun get()
}