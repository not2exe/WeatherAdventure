package com.weatheradventure.data.remoteLocations

import com.weatheradventure.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AutocompleteApi {
    @GET("v1/geocode/autocomplete")
    suspend fun get(
        @Query(Constants.TEXT) input: String,
        @Query(Constants.LANG) lang: String = Constants.RU,
        @Query(Constants.KEY) key: String = Constants.LOCATIONS_API_KEY
    ): Response<LocationResponseModel>
}