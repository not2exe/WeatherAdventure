package com.weatheradventure.data.remoteWeather

import com.weatheradventure.Constants
import com.weatheradventure.data.remoteWeather.model.WeatherResponseOneCallModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET(Constants.ONE_CALL_URL)
    suspend fun getCurrentWeather(
        @Query(Constants.LAT) lat: Double,
        @Query(Constants.LON) lon: Double,
        @Query(Constants.EXCLUDE) exclude: String = Constants.EXCLUDE_ONE_CALL,
        @Query(Constants.UNITS) units: String = Constants.METRIC,
        @Query(Constants.LANG) language: String = Constants.RU,
        @Query(Constants.APP_ID) apiKey: String = Constants.WEATHER_API_KEY,
    ): Response<WeatherResponseOneCallModel>
}