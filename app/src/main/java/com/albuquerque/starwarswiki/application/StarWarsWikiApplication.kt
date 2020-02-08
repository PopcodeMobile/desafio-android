package com.albuquerque.starwarswiki.application

import android.app.Application
import com.albuquerque.starwarswiki.BuildConfig.DEBUG
import com.facebook.stetho.Stetho

class StarWarsWikiApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupStetho()
    }


    private fun setupStetho() {
        if (DEBUG) {
            Stetho.initialize(
                Stetho.newInitializerBuilder(this).apply {
                    enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this@StarWarsWikiApplication))
                }.build()
            )
        }
    }

}