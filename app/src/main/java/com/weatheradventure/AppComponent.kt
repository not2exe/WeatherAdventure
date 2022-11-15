package com.weatheradventure

import android.app.Application
import android.content.Context
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