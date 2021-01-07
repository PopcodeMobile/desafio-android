package com.github.weslleystos.features.details.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.weslleystos.features.details.service.DetailsService
import com.github.weslleystos.shared.entities.Planet
import com.github.weslleystos.shared.entities.Specie

class DetailsViewModel : ViewModel() {
    val planetLiveData: MutableLiveData<Pair<Boolean, Planet?>> = MutableLiveData()
    val specieLiveData: MutableLiveData<Pair<Boolean, Specie?>> = MutableLiveData()

    fun getHomeWorld(url: String) {
        DetailsService().getHomeWorld(url, planetLiveData)
    }

    fun getSpecie(url: String) {
        DetailsService().getSpecie(url, specieLiveData)
    }
}