package io.github.untungs.weatherapp.ui

import android.app.Application
import io.github.untungs.weatherapp.extensions.DelegatesExt

class App : Application() {

    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}