package com.weatheradventure.data.remoteWeather

import com.weatheradventure.data.CurrentLocationCache
import com.weatheradventure.data.remoteWeather.model.WeatherResponseOneCallModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteWeatherRepo @Inject constructor(
    private val weatherApi: WeatherApi,
    private val cache: CurrentLocationCache,
) {
    val weatherModel = MutableStateFlow<WeatherResponseOneCallModel?>(null)
    val stateOfResponse: MutableStateFlow<StateOfResponse> =
        MutableStateFlow(StateOfResponse.SUCCESS)

    suspend fun get() = withContext(Dispatchers.IO) {
        try {
            val resultResponse =
                weatherApi.getCurrentWeather(cache.getLatitude(), cache.getLongitude())
            if (resultResponse.isSuccessful) {
                weatherModel.value = resultResponse.body()
                stateOfResponse.value = StateOfResponse.SUCCESS
                return@withContext
            }
            when (resultResponse.code()) {
                in 400..499 -> {
                    stateOfResponse.value = StateOfResponse.CLIENT_ERROR
                }
                in 500..599 -> {
                    stateOfResponse.value = StateOfResponse.SERVER_ERROR
                }
            }
        } catch (internetError: UnknownHostException) {
            stateOfResponse.value = StateOfResponse.CONNECTION_ERROR
        } catch (timeout: SocketTimeoutException) {
            stateOfResponse.value = StateOfResponse.TIMEOUT
        } catch (e: Exception) {
            stateOfResponse.value = StateOfResponse.FAILURE
        }
    }
}