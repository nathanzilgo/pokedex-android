package com.zilgo.pokedex

import android.app.Application
import dagger.hilt.android.qualifiers.ApplicationContext

val prefs: Prefs by lazy {
    App.prefs!!
}

class App : Application() {

    companion object {
        var prefs: Prefs? = null
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefs = Prefs(super.getApplicationContext())
    }
}