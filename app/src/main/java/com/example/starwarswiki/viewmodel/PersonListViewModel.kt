package com.example.starwarswiki.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarswiki.database.PersonDao
import com.example.starwarswiki.network.NetworkObject
import com.example.starwarswiki.network.NetworkPerson
import com.example.starwarswiki.network.PersonNetworkService
import com.example.starwarswiki.repository.PersonListRepository
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.lang.Exception

class PersonListViewModel(val database: PersonDao,
                          application: Application) : ViewModel() {

    private var viewModelJob = Job()

    private val uiCoroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val listRepository = PersonListRepository(database)

    val personList = listRepository.personList

    private val _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val _showSnackbarEvent = MutableLiveData<Boolean>()

    val showSnackbarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        refreshFromRepository()
    }

    private fun refreshFromRepository(){
        viewModelScope.launch {
            try {
                listRepository.refreshList()
                _eventNetworkError.value=false
                _isNetworkErrorShown.value =false
                Timber.d("Refresh sucessfully !")
            }
            catch (networkError: IOException){
                if(personList.value!!.isEmpty())
                    _eventNetworkError.value = true
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

    fun onClear(){
        uiCoroutineScope.launch {
            clear()
            _showSnackbarEvent.value = true
        }
    }

    suspend fun clear(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

    fun doneShowingSnackbar(){
        _showSnackbarEvent.value = false
    }
}
