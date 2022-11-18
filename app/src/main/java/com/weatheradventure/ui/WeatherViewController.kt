package com.weatheradventure.ui

import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatheradventure.R
import com.example.weatheradventure.databinding.DrawerHeaderLayoutBinding
import com.example.weatheradventure.databinding.FragmentWeatherBinding
import com.google.android.material.snackbar.Snackbar
import com.weatheradventure.Constants
import com.weatheradventure.data.remoteWeather.StateOfResponse
import com.weatheradventure.di.scopes.ViewScope
import com.weatheradventure.domain.MyFactory
import com.weatheradventure.domain.model.DailyWeatherModel
import com.weatheradventure.domain.model.UVI
import com.weatheradventure.ui.stateholders.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@ViewScope
class WeatherViewController @Inject constructor(
    private val fragment: WeatherFragment,
    private val weatherBinding: FragmentWeatherBinding,
    private val drawerLayoutBinding: DrawerHeaderLayoutBinding,
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModelFactory: WeatherViewModel.Factory,
    private val adapter: WeekWeatherAdapter,
) {
    private val context = weatherBinding.root.context
    private val viewModel by fragment.viewModels<WeatherViewModel> {
        MyFactory(
            fragment
        ) { handle: SavedStateHandle ->
            viewModelFactory.create(handle)
        }
    }

    fun setUpViews() {
        setupRv()
        setupObservers()
        setupRefresh()
        viewModel.getWeather()
    }

    private fun setupObservers() = with(weatherBinding) {
        viewModel.currentWeather.observe(viewLifecycleOwner) { currentWeatherModel ->
            setupCurrentWeather(currentWeatherModel)
            setupDrawerHeader(currentWeatherModel)
        }
        viewModel.weeklyWeather.observe(viewLifecycleOwner) { list ->
            adapter.updateList(list)
        }
        viewModel.stateOfResponse.observe(viewLifecycleOwner) { state ->
            val id = when (state) {
                StateOfResponse.SERVER_ERROR -> R.string.server_error
                StateOfResponse.TIMEOUT -> R.string.timeout
                StateOfResponse.CLIENT_ERROR -> R.string.client_error
                StateOfResponse.CONNECTION_ERROR -> R.string.connection_error
                StateOfResponse.FAILURE -> R.string.something_went_wrong
                StateOfResponse.SUCCESS -> null
            }
            refreshLayout.isRefreshing = false
            showSnackbar(id)
        }
        viewModel.isGeo.observe(viewLifecycleOwner) {
            viewModel.getWeather()
        }
    }

    private fun setupDrawerHeader(currentWeather: DailyWeatherModel) = with(drawerLayoutBinding) {
        selectedPlaceTv.text = currentWeather.place.city
        if (currentWeather.currentWeatherIconId != null) {
            currentWeatherIv.setImageResource(currentWeather.currentWeatherIconId)
        }
        if (currentWeather.degrees != null) {
            currentDegreesTv.text = currentWeather.degrees.toDegrees()
        }
    }

    private fun setupRefresh() {
        weatherBinding.refreshLayout.setOnRefreshListener {
            viewModel.getWeather()
        }
    }

    private fun setupRv() = with(weatherBinding) {
        weeklyWeatherRv.layoutManager = LinearLayoutManager(fragment.context)
        weeklyWeatherRv.adapter = adapter
    }

    private fun setupCurrentWeather(currentWeather: DailyWeatherModel) {
        setupDegrees(currentWeather.degrees)
        setupFeelsLike(currentWeather.feelsLike)
        setupPlace(currentWeather.place.city)
        setupRangeDegrees(currentWeather.degreesDay, currentWeather.degreesNight)
        setupCurrentDate(currentWeather.date)
        setupSunBehaviour(currentWeather.sunrise, currentWeather.sunset)
        setupHumidity(currentWeather.humidity)
        setupWindSpeed(currentWeather.windSpeed)
        setupUVIndex(currentWeather.uvi)
        setupIcon(currentWeather.currentWeatherIconId)
    }

    private fun setupFeelsLike(feelsLike: Int?) {
        if (feelsLike != null) {
            weatherBinding.feelsLikeTv.text =
                context.getString(R.string.feels_like) + " " + feelsLike.toDegrees()
        }
    }

    private fun setupDegrees(degrees: Int?) {
        if (degrees != null) {
            weatherBinding.mainDegreesTv.text = degrees.toDegrees()
        }
    }


    private fun setupPlace(place: String) = run { weatherBinding.placeTv.text = place }

    private fun setupRangeDegrees(degreesDay: Int, degreesNight: Int) {
        weatherBinding.rangeDegreesTv.text =
            "${degreesDay.toDegrees()} / ${degreesNight.toDegrees()}"
    }

    private fun setupCurrentDate(date: Long) {
        val format = SimpleDateFormat(Constants.DATE_PATTERN, Locale(Constants.RU))
        weatherBinding.dateTv.text = format.format(date)
    }

    private fun setupSunBehaviour(sunrise: Long, sunset: Long) {
        val format = SimpleDateFormat(Constants.TIME_PATTERN, Locale(Constants.RU))
        weatherBinding.sunriseDateTv.text = format.format(sunrise)
        weatherBinding.sunsetDateTv.text = format.format(sunset)
    }

    private fun setupIcon(id: Int?) {
        if (id != null) {
            weatherBinding.stateOfWeatherIv.setImageResource(id)
        }
    }

    private fun setupHumidity(humidity: Int) {
        weatherBinding.humidityTv.text = humidity.toPercents()
    }

    private fun setupUVIndex(uvi: UVI) {
        val idString =
            when (uvi) {
                UVI.UNKNOWN -> R.string.unknown
                UVI.LOW -> R.string.low
                UVI.MODERATE -> R.string.moderate
                UVI.HIGH -> R.string.high
                UVI.VERY_HIGH -> R.string.very_high
                UVI.EXTREME -> R.string.extreme
            }
        weatherBinding.UVIndexTv.text = context.getString(idString)
    }

    private fun setupWindSpeed(speed: Int) {
        weatherBinding.windSpeedTv.text = speed.toString() + context.getString(R.string.km_h)
    }


    private fun showSnackbar(@StringRes id: Int?) {
        if (id == null) return
        Snackbar.make(
            fragment.requireActivity().findViewById(android.R.id.content),
            id,
            Snackbar.LENGTH_LONG
        ).show()
    }


    private fun Int.toDegrees(): String = "$this°"
    private fun Int.toPercents(): String = "$this%"
}