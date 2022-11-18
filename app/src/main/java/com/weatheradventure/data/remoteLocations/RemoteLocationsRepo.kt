package com.weatheradventure.data.remoteLocations

import androidx.lifecycle.MutableLiveData
import com.weatheradventure.data.remoteWeather.StateOfResponse
import com.weatheradventure.di.scopes.AppScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@AppScope
class RemoteLocationsRepo @Inject constructor(private val autocompleteApi: AutocompleteApi) {

    val predicts = MutableLiveData<LocationResponseModel>()
    val stateOfResponse: MutableLiveData<StateOfResponse> =
        MutableLiveData()

    suspend fun get(input: String) = withContext(Dispatchers.IO) {
        try {
            val resultResponse = autocompleteApi.get(input)
            if (resultResponse.isSuccessful) {
                predicts.postValue(resultResponse.body())
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