package com.github.weslleystos.features.list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.weslleystos.features.list.services.PeopleListService
import com.github.weslleystos.shared.entities.People
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PeopleListViewModel : ViewModel() {
    val peoplesLiveData: MutableLiveData<Pair<Boolean, List<People>?>> = MutableLiveData()

    fun getAll(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            PeopleListService().getAll(page, peoplesLiveData)
        }
    }
}