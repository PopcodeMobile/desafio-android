package com.matheussilas97.starwarsapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = applicationContext

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: Context? = null
            private set

    }
}