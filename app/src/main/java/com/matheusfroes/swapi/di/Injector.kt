package com.matheusfroes.swapi.di

import com.matheusfroes.swapi.di.module.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, DataModule::class, NetworkModule::class,
    PeopleModule::class])
interface Injector