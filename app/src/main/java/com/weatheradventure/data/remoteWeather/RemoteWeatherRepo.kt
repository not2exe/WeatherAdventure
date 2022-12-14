package com.weatheradventure.data.remoteWeather

import androidx.lifecycle.MutableLiveData
import com.weatheradventure.data.CurrentLocationCache
import com.weatheradventure.data.remoteWeather.model.WeatherResponseOneCallModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteWeatherRepo @Inject constructor(
    private val weatherApi: WeatherApi,
    private val cache: CurrentLocationCache,
) {
    val weatherModel = MutableLiveData<WeatherResponseOneCallModel>()
    val stateOfResponse: MutableLiveData<StateOfResponse> =
        MutableLiveData()

    suspend fun get() = withContext(Dispatchers.IO) {
        try {
            val resultResponse =
                weatherApi.getCurrentWeather(cache.getLatitude(), cache.getLongitude())
            if (resultResponse.isSuccessful) {
                weatherModel.postValue(resultResponse.body())
                stateOfResponse.postValue(StateOfResponse.SUCCESS)
                return@withContext
            }
            when (resultResponse.code()) {
                in 400..499 -> {
                    stateOfResponse.postValue(StateOfResponse.CLIENT_ERROR)
                }
                in 500..599 -> {
                    stateOfResponse.postValue(StateOfResponse.SERVER_ERROR)
                }
            }
        } catch (internetError: UnknownHostException) {
            stateOfResponse.postValue(StateOfResponse.CONNECTION_ERROR)
        } catch (timeout: SocketTimeoutException) {
            stateOfResponse.postValue(StateOfResponse.TIMEOUT)
        } catch (e: Exception) {
            stateOfResponse.postValue(StateOfResponse.FAILURE)
        }
    }
}