package com.weatheradventure.ui.stateholders

import androidx.lifecycle.*
import com.weatheradventure.data.CurrentLocationCache
import com.weatheradventure.data.remoteWeather.RemoteWeatherRepo
import com.weatheradventure.data.remoteWeather.StateOfResponse
import com.weatheradventure.data.remoteWeather.model.WeatherResponseOneCallModel
import com.weatheradventure.domain.MapToDomain
import com.weatheradventure.domain.model.DailyWeatherModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch


class WeatherViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val remoteWeatherRepo: RemoteWeatherRepo,
    private val mapToDomain: MapToDomain,
    private val cache: CurrentLocationCache
) : ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): WeatherViewModel
    }
    val currentWeather: LiveData<DailyWeatherModel> =
        Transformations.switchMap(remoteWeatherRepo.weatherModel) {
            handleCurrent(
                it
            )
        }

    val weeklyWeather: LiveData<List<DailyWeatherModel>> =
        Transformations.switchMap(remoteWeatherRepo.weatherModel) { handleWeekly(it) }


    val stateOfResponse = Transformations.switchMap(remoteWeatherRepo.stateOfResponse) {
        MutableLiveData<StateOfResponse>(it)
    }

    val isGeo: LiveData<Boolean> =
        Transformations.switchMap(cache.isGeoLiveData) { MutableLiveData<Boolean>(it) }

    fun getWeather() {
        viewModelScope.launch {
            remoteWeatherRepo.get()
        }
    }


    private fun handleCurrent(responseModel: WeatherResponseOneCallModel): LiveData<DailyWeatherModel> =
        MutableLiveData<DailyWeatherModel>(
            mapToDomain.toCurrentDaily(
                responseModel.current,
                responseModel.daily.first()
            )
        )

    private fun handleWeekly(responseModel: WeatherResponseOneCallModel): LiveData<List<DailyWeatherModel>> =
        MutableLiveData<List<DailyWeatherModel>>(responseModel.daily.map {
            mapToDomain.dailyResponseToDailyDomain(
                it
            )
        })

}