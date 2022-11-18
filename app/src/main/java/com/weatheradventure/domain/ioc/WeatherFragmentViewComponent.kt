package com.weatheradventure.domain.ioc

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.Navigator
import com.weatheradventure.Constants
import com.weatheradventure.di.scopes.ViewScope
import com.weatheradventure.ui.WeatherFragment
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Named

@ViewScope
@Subcomponent(modules = [BindingModules::class])
interface WeatherFragmentViewComponent {
    @dagger.Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance view: View,
            @BindsInstance viewLifecycleOwner: LifecycleOwner
        ): WeatherFragmentViewComponent
    }

    fun inject(fragment: WeatherFragment)
}