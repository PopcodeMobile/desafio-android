package com.github.weslleystos.features.list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.weslleystos.features.list.services.PeopleListService
import com.github.weslleystos.shared.entities.People
import com.github.weslleystos.shared.utils.LiveDateState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PeopleListViewModel : ViewModel() {
    val peoplesLiveData: MutableLiveData<Pair<LiveDateState, List<People>?>> = MutableLiveData()

    fun getAll(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            PeopleListService().getAll(page, peoplesLiveData)
        }
    }

    fun clearLiveData() {
        peoplesLiveData.value = Pair(LiveDateState.HANDLED, null)
    }
}