package com.weatheradventure.data.remoteWeather

import com.weatheradventure.data.Cache
import com.weatheradventure.data.remoteWeather.model.WeatherResponseOneCallModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class RemoteWeatherRepoImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val cache: Cache,
) :
    RemoteWeatherRepo {
    override val weatherModel = MutableStateFlow<WeatherResponseOneCallModel?>(null)
//    override val stateOfResponse: MutableStateFlow<StateOfResponse> = MutableStateFlow()
//        get() = TODO("Not yet implemented")

    override suspend fun get() {
        try {
            val resultResponse =
                weatherApi.getCurrentWeather(cache.getLatitude(), cache.getLongitude())
            if (resultResponse.isSuccessful) {
                weatherModel.value = resultResponse.body()
            }
        } catch (e: Exception) {

        }
    }
}