package com.matheusfroes.swapi.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class People

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Apiary