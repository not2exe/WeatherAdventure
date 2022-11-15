package com.weatheradventure.data

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import com.weatheradventure.AppScope
import com.weatheradventure.Constants
import javax.inject.Inject

@AppScope
class Cache @Inject constructor(context: Context) {
    private val sharedCords =
        context.getSharedPreferences(Constants.SHARED_CORDS, Context.MODE_PRIVATE)

    fun setLatitude(latitude: Double) =
        sharedCords.edit { putFloat(Constants.LAT, latitude.toFloat()) }


    fun setLongitude(longitude: Double) =
        sharedCords.edit { putFloat(Constants.LON, longitude.toFloat()) }

    fun getLatitude(): Double = sharedCords.getFloat(Constants.LAT, Constants.DEF_LAT).toDouble()
    fun getLongitude(): Double = sharedCords.getFloat(Constants.LON, Constants.DEF_LON).toDouble()
}