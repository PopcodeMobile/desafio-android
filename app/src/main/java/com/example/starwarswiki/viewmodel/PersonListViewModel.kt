package com.example.starwarswiki.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.starwarswiki.database.PersonDao
import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.network.NetworkObject
import com.example.starwarswiki.network.NetworkPerson
import com.example.starwarswiki.network.PersonNetworkService
import com.example.starwarswiki.repository.PersonListRepository
import com.example.starwarswiki.ui.PersonListFragment
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

//    private var _searchText = MutableLiveData<String?>("")
//
//    val searchText: LiveData<String?>
//        get() = _searchText

    private val _personSearch = MutableLiveData<List<PersonModel>>()

    val personSearch: LiveData<List<PersonModel>>
        get() = _personSearch


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

    private val _detailPerson = MutableLiveData<Int>()

    val detailPerson: LiveData<Int>
        get() = _detailPerson

    fun onPersonClicked(id: Int){
        _detailPerson.value = id
    }

    fun onPersonDetailed(){
        _detailPerson.value = null
    }

    private val _favoriteId = MutableLiveData<Int>()

    val favoriteId:LiveData<Int>
        get() = _favoriteId

    fun onFavoriteClicked(id: Int){
        _favoriteId.value = id
    }
    fun onInputText(string: String){
        viewModelScope.launch {
            _personSearch.value = listRepository.peopleSearched(string)
        }
    }
}
