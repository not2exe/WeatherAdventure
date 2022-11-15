package com.weatheradventure.ui

import android.app.Activity
import android.content.Context
import com.example.weatheradventure.databinding.FragmentWeatherBinding
import com.weatheradventure.Constants
import java.text.SimpleDateFormat
import java.util.*

class WeatherViewController(
    private val binding: FragmentWeatherBinding,
    private val activity: Activity,
    private val permissionId: Int = Constants.REQUEST_GEO_CODE,
    private val context: Context = binding.root.context,
) {

    fun setUpViews() {
    }

    private fun setupDegrees(degrees: Int) =
        run { binding.mainDegreesTv.text = degrees.toDegrees() }

    private fun setupPlace(place: String) = run { binding.placeTv.text = place }

    private fun setupRangeDegrees(degreesMin: Int, degreesMax: Int) {
        binding.rangeDegreesTv.text =
            "${degreesMax.toDegrees()} / ${degreesMin.toDegrees()}"
    }

    private fun setupCurrentDate(date: Long) {
        val format = SimpleDateFormat(Constants.DATE_PATTERN, Locale.getDefault())
        binding.dateTv.text = format.format(date)
    }

    private fun setupSunBehaviour(sunrise: Long, sunset: Long) {
        val format = SimpleDateFormat(Constants.TIME_PATTERN, Locale.getDefault())
        binding.sunriseDateTv.text = format.format(sunrise)
        binding.sunsetDateTv.text = format.format(sunset)
    }




    private fun Int.toDegrees(): String = "$this°"
}