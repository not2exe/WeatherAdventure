package com.weatheradventure.domain.ioc

import android.view.View
import com.example.weatheradventure.databinding.FragmentSearchLocationsBinding
import com.example.weatheradventure.databinding.FragmentWeatherBinding
import com.weatheradventure.Constants
import com.weatheradventure.di.scopes.ViewScope
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
interface BindingModules {
    companion object {
        @Provides
        @ViewScope
        fun provideWeatherBinding(@Named(Constants.WEATHER) view: View): FragmentWeatherBinding =
            FragmentWeatherBinding.bind(view)

        @Provides
        @ViewScope
        fun provideSearchLocationsBinding(@Named(Constants.SEARCH) view: View): FragmentSearchLocationsBinding =
            FragmentSearchLocationsBinding.bind(view)
    }
}