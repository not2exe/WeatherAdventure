package com.weatheradventure.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weatheradventure.R
import com.google.android.gms.location.LocationServices
import com.weatheradventure.Constants
import com.weatheradventure.WeatherApp
import com.weatheradventure.data.Cache
import com.weatheradventure.data.remoteWeather.RemoteWeatherRepoImpl
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var cache: Cache


    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    @Inject
    lateinit var remoteWeatherRepo: RemoteWeatherRepoImpl


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as WeatherApp).appComponent.activityComponent().inject(this)
        if (!checkPermissions()) {
            requestPermissions()
        } else {
            requestLocation()
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constants.REQUEST_GEO_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            requestLocation()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            setLocation(it)
        }
    }

    private fun setLocation(location: Location) {
        cache.setLatitude(location.latitude)
        cache.setLongitude(location.longitude)
    }

    private fun checkPermissions(): Boolean =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            Constants.REQUEST_GEO_CODE
        )
    }
}