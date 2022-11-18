package com.weatheradventure.domain.ioc

import com.weatheradventure.di.scopes.ActivityScope
import com.weatheradventure.ui.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface ActivityComponent {
    fun inject(activity: MainActivity)
    fun weatherFragmentComponentFactory(): WeatherFragmentComponent.Factory
}