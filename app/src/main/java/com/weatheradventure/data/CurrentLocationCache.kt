package com.weatheradventure.data

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.weatheradventure.Constants
import com.weatheradventure.di.scopes.AppScope
import javax.inject.Inject

@AppScope
class CurrentLocationCache @Inject constructor(private val context: Context) {
    private val sharedCords =
        context.getSharedPreferences(Constants.SHARED_CORDS, Context.MODE_PRIVATE)

    val isGeoLiveData = MutableLiveData<Boolean>(false)

    fun setCords(latitude: Double, longitude: Double, isGeo: Boolean) {
        setLatitude(latitude)
        setLongitude(longitude)
        isGeoLiveData.value = isGeo
    }

    private fun setLatitude(latitude: Double) {
        sharedCords.edit { putFloat(Constants.LAT, latitude.toFloat()) }
    }


    private fun setLongitude(longitude: Double) {
        sharedCords.edit { putFloat(Constants.LON, longitude.toFloat()) }
    }

    fun getLatitude(): Double = sharedCords.getFloat(Constants.LAT, Constants.DEF_LAT).toDouble()
    fun getLongitude(): Double = sharedCords.getFloat(Constants.LON, Constants.DEF_LON).toDouble()
}