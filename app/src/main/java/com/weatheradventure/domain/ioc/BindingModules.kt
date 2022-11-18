package com.weatheradventure.domain.ioc

import android.view.View
import com.example.weatheradventure.R
import com.example.weatheradventure.databinding.DrawerHeaderLayoutBinding
import com.example.weatheradventure.databinding.FragmentWeatherBinding
import com.google.android.material.navigation.NavigationView
import com.weatheradventure.di.scopes.ViewScope
import com.weatheradventure.ui.WeatherFragment
import dagger.Module
import dagger.Provides

@Module
interface BindingModules {
    companion object {
        @Provides
        @ViewScope
        fun provideWeatherBinding(view: View): FragmentWeatherBinding =
            FragmentWeatherBinding.bind(view)

        @Provides
        @ViewScope
        fun provideDrawerBinding(fragment: WeatherFragment): DrawerHeaderLayoutBinding =
            DrawerHeaderLayoutBinding.bind(
                (fragment.requireActivity()
                    .findViewById(R.id.navigationView) as NavigationView).getHeaderView(0)
            )
    }
}