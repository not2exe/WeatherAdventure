package com.weatheradventure.di.modules

import com.weatheradventure.Constants
import com.weatheradventure.data.remoteWeather.WeatherApi
import com.weatheradventure.di.scopes.AppScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
interface NetworkModule {
    companion object {
        @Provides
        @AppScope
        fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        @Provides
        @AppScope
        fun provideApiService(retrofit: Retrofit): WeatherApi =
            retrofit.create(WeatherApi::class.java)
    }
}