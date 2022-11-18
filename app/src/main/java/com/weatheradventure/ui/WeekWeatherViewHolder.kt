package com.weatheradventure.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheradventure.R
import com.example.weatheradventure.databinding.DailyItemBinding
import com.weatheradventure.Constants
import com.weatheradventure.domain.model.DailyWeatherModel
import java.text.SimpleDateFormat
import java.util.*

class WeekWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = DailyItemBinding.bind(itemView)
    fun bind(dailyWeatherModel: DailyWeatherModel, isFirst: Boolean) = with(binding) {
        val formatter = SimpleDateFormat(Constants.DAY_OF_THE_WEEK_PATTERN, Locale(Constants.RU))
        val textDayOfTheWeek =
            if (isFirst) itemView.context.getString(R.string.today) else formatter.format(
                dailyWeatherModel.date
            ).replaceFirstChar { it.uppercaseChar() }
        dayOfTheWeekTv.text = textDayOfTheWeek
        humidityItemTv.text = dailyWeatherModel.humidity.toPercents()
        dayDegreesTv.text = dailyWeatherModel.degreesDay.toDegrees()
        nightDegreesTv.text = dailyWeatherModel.degreesNight.toDegrees()
        if (dailyWeatherModel.dayIconId != null) {
            dayWeatherIv.setImageResource(dailyWeatherModel.dayIconId)
        }
        if (dailyWeatherModel.nightIconId != null) {
            nightWeatherIv.setImageResource(dailyWeatherModel.nightIconId)
        }
    }

    private fun Int.toDegrees(): String = "$this°"
    private fun Int.toPercents(): String = "$this%"
}