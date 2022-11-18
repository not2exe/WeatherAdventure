package com.weatheradventure.domain.ioc

import com.weatheradventure.di.scopes.FragmentScope
import com.weatheradventure.ui.SearchLocationsFragment
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface SearchFragmentComponent {
    @dagger.Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: SearchLocationsFragment): SearchFragmentComponent
    }

    fun searchFragmentViewComponentFactory(): SearchFragmentViewComponent.Factory
}