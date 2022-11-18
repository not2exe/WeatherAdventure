package com.weatheradventure.domain.ioc

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.weatheradventure.Constants
import com.weatheradventure.di.scopes.ViewScope
import com.weatheradventure.ui.SearchLocationsFragment
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Named

@ViewScope
@Subcomponent(modules = [BindingModules::class])
interface SearchFragmentViewComponent {
    @dagger.Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance @Named(Constants.SEARCH) view: View,
            @BindsInstance viewLifecycleOwner: LifecycleOwner
        ): SearchFragmentViewComponent
    }

    fun inject(searchFragment: SearchLocationsFragment)
}