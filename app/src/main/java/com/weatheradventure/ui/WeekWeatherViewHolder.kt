package com.weatheradventure.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheradventure.R
import com.example.weatheradventure.databinding.DailyItemBinding
import com.google.android.material.textview.MaterialTextView
import com.weatheradventure.Constants
import com.weatheradventure.domain.model.DailyWeatherModel
import com.weatheradventure.domain.model.UVI
import java.text.SimpleDateFormat
import java.util.*

class WeekWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = DailyItemBinding.bind(itemView)
    fun bind(dailyWeatherModel: DailyWeatherModel, isFirst: Boolean) = with(binding) {
        itemView.setOnClickListener {
            showDialog(dailyWeatherModel)
        }
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

    private fun showDialog(dailyWeatherModel: DailyWeatherModel) {
        val dialog = Dialog(binding.root.context)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val format = SimpleDateFormat(Constants.TIME_PATTERN, Locale(Constants.RU))
        dialog.setContentView(R.layout.dialog_daily_info)
        dialog.findViewById<MaterialTextView>(R.id.sunriseDateTv).text =
            format.format(dailyWeatherModel.sunrise)
        dialog.findViewById<MaterialTextView>(R.id.sunsetDateTv).text =
            format.format(dailyWeatherModel.sunset)
        val idString =
            when (dailyWeatherModel.uvi) {
                UVI.UNKNOWN -> R.string.unknown
                UVI.LOW -> R.string.low
                UVI.MODERATE -> R.string.moderate
                UVI.HIGH -> R.string.high
                UVI.VERY_HIGH -> R.string.very_high
                UVI.EXTREME -> R.string.extreme
            }
        dialog.findViewById<MaterialTextView>(R.id.UVIndexTv).setText(idString)
        dialog.findViewById<MaterialTextView>(R.id.humidityTv).text =
            dailyWeatherModel.humidity.toPercents()
        dialog.findViewById<MaterialTextView>(R.id.windSpeedTv).text =
            dailyWeatherModel.windSpeed.toString() + itemView.context.getString(R.string.km_h)
        dialog.show()

    }

    private fun Int.toDegrees(): String = "$this°"
    private fun Int.toPercents(): String = "$this%"

}