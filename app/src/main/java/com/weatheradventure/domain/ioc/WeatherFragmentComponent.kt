package com.weatheradventure.domain.ioc

import androidx.fragment.app.Fragment
import com.weatheradventure.di.scopes.FragmentScope
import com.weatheradventure.ui.WeatherFragment
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [AdaptersModule::class])
interface WeatherFragmentComponent {
    @dagger.Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: WeatherFragment): WeatherFragmentComponent
    }

    fun weatherFragmentViewComponentFactory(): WeatherFragmentViewComponent.Factory
}