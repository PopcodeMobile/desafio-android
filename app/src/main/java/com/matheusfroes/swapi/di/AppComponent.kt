package com.matheusfroes.swapi.di

import com.matheusfroes.swapi.di.module.*
import dagger.Component

@Component(modules = [(AppModule::class), (DataModule::class), (ViewModelModule::class),
    (NetworkModule::class), (PeopleModule::class), (ApiaryModule::class)])
interface AppComponent
