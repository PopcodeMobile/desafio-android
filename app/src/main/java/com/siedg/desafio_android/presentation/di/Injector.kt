package com.siedg.desafio_android.presentation.di

import com.siedg.desafio_android.presentation.di.home.HomeSubComponent

interface Injector {
    fun createHomeSubComponent(): HomeSubComponent
}