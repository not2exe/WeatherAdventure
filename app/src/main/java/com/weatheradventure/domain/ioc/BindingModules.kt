package com.weatheradventure.domain.ioc

import android.view.View
import com.example.weatheradventure.R
import com.example.weatheradventure.databinding.DrawerHeaderLayoutBinding
import com.example.weatheradventure.databinding.FragmentSearchLocationsBinding
import com.example.weatheradventure.databinding.FragmentWeatherBinding
import com.google.android.material.navigation.NavigationView
import com.weatheradventure.Constants
import com.weatheradventure.di.scopes.ViewScope
import com.weatheradventure.ui.WeatherFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
interface BindingModules {
    companion object {
        @Provides
        @ViewScope
        fun provideWeatherBinding(@Named(Constants.WEATHER)view: View): FragmentWeatherBinding =
            FragmentWeatherBinding.bind(view)

        @Provides
        @ViewScope
        fun provideDrawerBinding(fragment: WeatherFragment): DrawerHeaderLayoutBinding =
            DrawerHeaderLayoutBinding.bind(
                (fragment.requireActivity()
                    .findViewById(R.id.navigationView) as NavigationView).getHeaderView(0)
            )

        @Provides
        @ViewScope
        fun provideSearchLocationsBinding(@Named(Constants.SEARCH)view: View): FragmentSearchLocationsBinding =
            FragmentSearchLocationsBinding.bind(view)
    }
}