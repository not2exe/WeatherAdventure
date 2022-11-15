package com.weatheradventure.data.remoteWeather

import com.weatheradventure.Constants
import com.weatheradventure.data.Cache
import javax.inject.Inject

class RemoteWeatherRepoImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val cache: Cache
) :
    RemoteWeatherRepo {
    override suspend fun get() {
        try {
            val resultResponse =
                weatherApi.getCurrentWeather(cache.getLatitude(), cache.getLongitude())
            println( resultResponse.errorBody())
        } catch (e: Exception) {

        }
    }
}