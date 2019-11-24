package com.example.starwarswiki

import android.app.Application
import timber.log.Timber

class PersonListApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
