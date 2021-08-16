package com.siedg.desafio_android.domain

import com.siedg.desafio_android.data.model.SpecieModel

interface SpecieRepository {
    suspend fun getSpecieList(): List<SpecieModel>?
}