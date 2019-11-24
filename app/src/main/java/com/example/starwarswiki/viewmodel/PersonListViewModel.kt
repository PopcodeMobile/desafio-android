package com.example.starwarswiki.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starwarswiki.network.NetworkObject
import com.example.starwarswiki.network.NetworkPerson
import com.example.starwarswiki.network.PersonNetworkService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class PersonListViewModel(application: Application) : ViewModel() {

    private var viewModelJob = Job()

    val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        getPersonListProperties()
    }

    private fun getPersonListProperties(){
        coroutineScope.launch {
            var getPersonListDeferred = PersonNetworkService.personList.getPersonList()
            try {
                var listResult = getPersonListDeferred.await()
                _response.value = "Sucess: ${listResult.count} items retrieved !"
            }
            catch (e: Exception){
                _response.value = "Failure: "+ e.message
            }
        }
    }

    fun onNetworkErrorShown(){
        _isNetworkErrorShown.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
