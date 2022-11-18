package com.weatheradventure.di.modules

import com.weatheradventure.Constants
import com.weatheradventure.data.remoteLocations.AutocompleteApi
import com.weatheradventure.data.remoteWeather.WeatherApi
import com.weatheradventure.di.scopes.AppScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
interface NetworkModule {
    companion object {
        @Provides
        @AppScope
        @Named(Constants.WEATHER)
        fun provideWeatherRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(Constants.WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        @Provides
        @AppScope
        @Named(Constants.AUTOCOMPLETE)
        fun provideAutocompleteRetrofit(): Retrofit =
            Retrofit.Builder().baseUrl(Constants.LOCATIONS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        @Provides
        @AppScope
        fun provideWeatherApiService(@Named(Constants.WEATHER) retrofit: Retrofit): WeatherApi =
            retrofit.create(WeatherApi::class.java)

        @Provides
        @AppScope
        fun provideAutocompleteApi(@Named(Constants.AUTOCOMPLETE) retrofit: Retrofit): AutocompleteApi =
            retrofit.create(AutocompleteApi::class.java)
    }
}