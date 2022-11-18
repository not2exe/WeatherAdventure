package com.weatheradventure.domain.ioc

import com.weatheradventure.di.scopes.FragmentScope
import com.weatheradventure.ui.WeekWeatherAdapter
import dagger.Module
import dagger.Provides

@Module
interface AdaptersModule {
    companion object {
        @Provides
        @FragmentScope
        fun provideWeekWeatherAdapter(): WeekWeatherAdapter = WeekWeatherAdapter()
    }
}