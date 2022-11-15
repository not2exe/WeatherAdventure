package com.weatheradventure

import android.app.Application

class WeatherApp : Application() {
    val appComponent by lazy { DaggerAppComponent.factory().create(this) }
}