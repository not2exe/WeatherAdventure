package com.weatheradventure.di

import android.content.Context
import com.weatheradventure.domain.ioc.ActivityComponent
import com.weatheradventure.di.scopes.AppScope
import com.weatheradventure.di.modules.LocationModule
import com.weatheradventure.di.modules.NetworkModule
import com.weatheradventure.domain.MapToDomain
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [NetworkModule::class, LocationModule::class])
interface AppComponent {
    @dagger.Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
    fun activityComponent(): ActivityComponent
}