package com.weatheradventure.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.weatheradventure.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.weatheradventure.Constants
import com.weatheradventure.data.CurrentLocationCache
import com.weatheradventure.di.WeatherApp
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var cache: CurrentLocationCache

    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as WeatherApp).appComponent.activityComponent().inject(this)
        setupDrawerLayout()
        requestLocation()
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
        fusedLocationClient.lastLocation.addOnCompleteListener(this) {
            if (checkPermissions()) {
                if (checkAvailabilityGeo()) {
                    setLocation(it.result)
                } else {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        R.string.need_to_turn_on_location,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            } else {
                requestPermissions()
            }
        }
    }

    private fun checkAvailabilityGeo(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
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

    private fun setupDrawerLayout() {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColorFromAttr(R.attr.appBarColor)))
        supportActionBar?.elevation = 0F
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcvMainContainer) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<NavigationView>(R.id.navigationView).setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(
            this,
            navController,
            findViewById<DrawerLayout>(R.id.drawer_layout)
        )
    }

    @ColorInt
    fun Context.getColorFromAttr(
        @AttrRes attrColor: Int,
        typedValue: TypedValue = TypedValue(),
        resolveRefs: Boolean = true
    ): Int {
        theme.resolveAttribute(attrColor, typedValue, resolveRefs)
        return typedValue.data
    }
}