package com.weatheradventure.ui

import androidx.recyclerview.widget.DiffUtil
import com.weatheradventure.domain.model.DailyWeatherModel

class DiffUtilCallbackImpl(
    private val oldList: List<DailyWeatherModel>,
    private val newList: List<DailyWeatherModel>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].date == newList[newItemPosition].date

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].date == newList[newItemPosition].date
}