package com.weatheradventure.domain

import android.location.Geocoder
import com.example.weatheradventure.R
import com.weatheradventure.Constants
import com.weatheradventure.data.CurrentLocationCache
import com.weatheradventure.data.remoteWeather.model.CurrentWeatherResponseModel
import com.weatheradventure.data.remoteWeather.model.WeatherResponseDailyModel
import com.weatheradventure.data.remoteWeather.model.WeatherResponseDescription
import com.weatheradventure.domain.model.DailyWeatherModel
import com.weatheradventure.domain.model.Place
import com.weatheradventure.domain.model.UVI
import javax.inject.Inject
import kotlin.math.roundToInt


class MapToDomain @Inject constructor(private val geocoder: Geocoder, cache: CurrentLocationCache) {
    private val addresses = geocoder.getFromLocation(cache.getLatitude(), cache.getLongitude(), 1)
    private val place = Place(
        address = addresses?.get(0)?.getAddressLine(0) ?: "",
        city = addresses?.get(0)?.locality ?: "",
        state = addresses?.get(0)?.adminArea ?: "",
        country = addresses?.get(0)?.countryName ?: ""
    )

    fun toCurrentDaily(
        currentWeatherResponseModel: CurrentWeatherResponseModel,
        weatherResponseDailyModel: WeatherResponseDailyModel
    ): DailyWeatherModel = DailyWeatherModel(
        date = toMs(currentWeatherResponseModel.date),
        place = place,
        degrees = currentWeatherResponseModel.temp.roundToInt(),
        feelsLike = currentWeatherResponseModel.feelsLike.roundToInt(),
        degreesNight = weatherResponseDailyModel.temp.night.roundToInt(),
        degreesDay = weatherResponseDailyModel.temp.day.roundToInt(),
        humidity = currentWeatherResponseModel.humidity,
        uvi = doubleToUVI(currentWeatherResponseModel.uvi),
        windSpeed = currentWeatherResponseModel.windSpeed.toInt(),
        description = currentWeatherResponseModel.weatherDescription[0].main,
        sunrise = toMs(weatherResponseDailyModel.sunrise),
        sunset = toMs(weatherResponseDailyModel.sunset),
        currentWeatherIconId = idToIconId(currentWeatherResponseModel.weatherDescription.first().iconId),
        dayIconId = idToIconId(getFirstDaily(weatherResponseDailyModel.weatherDescription)),
        nightIconId = idToIconId(
            getFirstNightly(weatherResponseDailyModel.weatherDescription),
        )
    )

    fun dailyResponseToDailyDomain(weatherResponseDailyModel: WeatherResponseDailyModel): DailyWeatherModel =
        DailyWeatherModel(
            date = toMs(weatherResponseDailyModel.date),
            place = place,
            degreesNight = weatherResponseDailyModel.temp.night.roundToInt(),
            degreesDay = weatherResponseDailyModel.temp.day.roundToInt(),
            humidity = weatherResponseDailyModel.humidity,
            uvi = doubleToUVI(weatherResponseDailyModel.uvi),
            windSpeed = weatherResponseDailyModel.windSpeed.roundToInt(),
            description = weatherResponseDailyModel.weatherDescription[0].main,
            sunrise = toMs(weatherResponseDailyModel.sunrise),
            sunset = toMs(weatherResponseDailyModel.sunset),
            dayIconId = idToIconId(getFirstDaily(weatherResponseDailyModel.weatherDescription)),
            nightIconId = idToIconId(getFirstNightly(weatherResponseDailyModel.weatherDescription)),
        )

    private fun doubleToUVI(uvi: Double): UVI =
        when (uvi) {
            in 0.0..2.0 -> UVI.LOW
            in 3.0..5.0 -> UVI.MODERATE
            in 6.0..7.0 -> UVI.HIGH
            in 8.0..10.0 -> UVI.VERY_HIGH
            in 11.0..Double.MAX_VALUE -> UVI.EXTREME
            else -> UVI.UNKNOWN
        }

    private fun toMs(date: Long): Long = date * 1000

    private fun getFirstDaily(list: List<WeatherResponseDescription>): String =
        list.firstOrNull { it.iconId.takeLast(1) == "d" }?.iconId
            ?: (list.first { it.iconId.takeLast(1) == "n" }.iconId.dropLast(1) + "d")


    private fun getFirstNightly(list: List<WeatherResponseDescription>): String =
        list.firstOrNull { it.iconId.takeLast(1) == "n" }?.iconId
            ?: (list.first { it.iconId.takeLast(1) == "d" }.iconId.dropLast(1) + "n")

    private fun idToIconId(id: String): Int? = when (id) {
        Constants.CLEAR_SKY_DAY_ID -> R.drawable.ic_sun
        Constants.CLEAR_SKY_NIGHT_ID -> R.drawable.ic_clear_night
        Constants.FEW_CLOUDS_DAY_ID -> R.drawable.ic_few_clouds_day
        Constants.FEW_CLOUDS_NIGHT_ID -> R.drawable.ic_few_clouds_night
        in arrayOf(
            Constants.SCATTERED_CLOUDS_DAY_ID,
            Constants.SCATTERED_CLOUDS_NIGHT_ID
        ) -> R.drawable.ic_cloud
        in arrayOf(
            Constants.BROKEN_CLOUDS_DAY_ID,
            Constants.BROKEN_CLOUDS_NIGHT_ID
        ) -> R.drawable.ic_broken_clouds
        in arrayOf(
            Constants.SHOWER_RAIN_DAY_ID,
            Constants.SHOWER_RAIN_NIGHT_ID
        ) -> R.drawable.ic_shower_rain
        Constants.RAIN_DAY_ID -> R.drawable.ic_rain_day
        Constants.RAIN_NIGHT_ID -> R.drawable.ic_rain_night
        in arrayOf(
            Constants.THUNDERSTORM_DAY_ID,
            Constants.THUNDERSTORM_NIGHT_ID
        ) -> R.drawable.ic_thunderstorm
        in arrayOf(
            Constants.SNOW_DAY_ID,
            Constants.SNOW_NIGHT_ID
        ) -> R.drawable.ic_snow_
        in arrayOf(
            Constants.MIST_DAY_ID,
            Constants.MIST_NIGHT_ID
        ) -> R.drawable.ic_mist
        else -> null
    }
}