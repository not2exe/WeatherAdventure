package com.weatheradventure.di.modules

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.weatheradventure.Constants
import com.weatheradventure.di.scopes.AppScope
import dagger.Module
import dagger.Provides
import java.util.*

@Module
interface LocationModule {
    companion object {
        @Provides
        @AppScope
        fun provideFusedLocationClient(context: Context): FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)

        @Provides
        fun provideAddress(context: Context): Geocoder =
            Geocoder(context, Locale(Constants.RU))
    }
}