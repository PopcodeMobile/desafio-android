package com.siedg.desafio_android.presentation.di.home

import com.siedg.desafio_android.presentation.ui.HomeFragment
import com.siedg.desafio_android.presentation.ui.MainActivity
import dagger.Subcomponent

@HomeScope
@Subcomponent(modules = [HomeModule::class])
interface HomeSubComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(homeFragment: HomeFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create(): HomeSubComponent
    }
}