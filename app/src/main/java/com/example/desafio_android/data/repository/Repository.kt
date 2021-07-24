package com.example.desafio_android.data.repository

import com.example.desafio_android.data.api.RequestApi
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val requestApi: RequestApi){
}