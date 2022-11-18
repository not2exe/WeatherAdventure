package com.weatheradventure.ui.stateholders

import androidx.lifecycle.*
import com.weatheradventure.data.remoteWeather.RemoteWeatherRepo
import com.weatheradventure.data.remoteWeather.StateOfResponse
import com.weatheradventure.domain.MapToDomain
import com.weatheradventure.domain.model.DailyWeatherModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class WeatherViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val remoteWeatherRepo: RemoteWeatherRepo,
    private val mapToDomain: MapToDomain
) : ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): WeatherViewModel
    }

    private val _currentWeather = MutableLiveData<DailyWeatherModel>()
    val currentWeather: LiveData<DailyWeatherModel> = _currentWeather

    private val _weeklyWeather = MutableLiveData<List<DailyWeatherModel>>()
    val weeklyWeather: LiveData<List<DailyWeatherModel>> = _weeklyWeather

    private val _stateOfResponse = MutableLiveData<StateOfResponse>()
    val stateOfResponse = _stateOfResponse

    fun getWeather() {
        viewModelScope.launch {
            remoteWeatherRepo.get()
            collectWeatherData()
        }
        viewModelScope.launch {
            collectStateOfResponse()
        }
    }

    private suspend fun collectWeatherData() {
        remoteWeatherRepo.weatherModel.collectLatest { responseModel ->
            if (responseModel != null) {
                _currentWeather.value = mapToDomain.toCurrentDaily(
                    responseModel.current,
                    responseModel.daily.first()
                )
                _weeklyWeather.value =
                    responseModel.daily.map { mapToDomain.dailyResponseToDailyDomain(it) }
            }
        }
    }

    private suspend fun collectStateOfResponse() {
        remoteWeatherRepo.stateOfResponse.collectLatest {
            _stateOfResponse.value = it
        }
    }
}