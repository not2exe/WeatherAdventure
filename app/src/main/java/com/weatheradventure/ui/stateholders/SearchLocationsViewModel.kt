package com.weatheradventure.ui.stateholders

import androidx.lifecycle.*
import com.weatheradventure.data.CurrentLocationCache
import com.weatheradventure.data.remoteLocations.OnePlaceModel
import com.weatheradventure.data.remoteLocations.RemoteLocationsRepo
import com.weatheradventure.data.remoteWeather.StateOfResponse
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class SearchLocationsViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val remoteLocationsRepo: RemoteLocationsRepo,
    private val cache: CurrentLocationCache
) :
    ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): SearchLocationsViewModel
    }

    val predicts: LiveData<List<OnePlaceModel>> =
        Transformations.switchMap(remoteLocationsRepo.predicts) { response ->
            MutableLiveData<List<OnePlaceModel>>(response.features.map { it.properties })
        }
    val stateOfResponse: LiveData<StateOfResponse> =
        Transformations.switchMap(remoteLocationsRepo.stateOfResponse) { currentStateOfResponse ->
            MutableLiveData(currentStateOfResponse)
        }

    fun getPredicts(input: String) {
        viewModelScope.launch {
            remoteLocationsRepo.get(input)
        }
    }

    fun selectPlace(onePlaceModel: OnePlaceModel) {
        cache.setCords(onePlaceModel.lat, onePlaceModel.lon, false)
    }
}