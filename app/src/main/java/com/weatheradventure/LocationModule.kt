package com.weatheradventure

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides

@Module
interface LocationModule {
    companion object {
        @Provides
        @AppScope
        fun provideFusedLocationClient(context: Context): FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
    }
}