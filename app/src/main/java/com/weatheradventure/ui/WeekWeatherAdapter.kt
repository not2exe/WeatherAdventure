package com.weatheradventure.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatheradventure.R
import com.weatheradventure.domain.model.DailyWeatherModel

class WeekWeatherAdapter : RecyclerView.Adapter<WeekWeatherViewHolder>() {
    private var list: List<DailyWeatherModel> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekWeatherViewHolder =
        WeekWeatherViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.daily_item, parent, false)
        )

    override fun onBindViewHolder(holder: WeekWeatherViewHolder, position: Int) =
        holder.bind(list[position], position == 0)


    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<DailyWeatherModel>) {
        val diffUtilCallback = DiffUtilCallbackImpl(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }

}