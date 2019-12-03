package com.example.starwarswiki.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.starwarswiki.database.PersonDao
import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.network.FavoriteNetworkObject
import com.example.starwarswiki.repository.PersonListRepository
import kotlinx.coroutines.*
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class PersonListViewModel(val database: PersonDao,
                          application: Application) : ViewModel() {

    private var viewModelJob = Job()

    private val uiCoroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val listRepository = PersonListRepository(database)

    val personList = listRepository.personList

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

    init {
        refreshFromRepository()
    }

    private fun refreshFromRepository(){
        viewModelScope.launch {
            try {
                listRepository.refreshList()
                _eventNetworkError.value=false
                _isNetworkErrorShown.value =false
                //Timber.d("Refresh sucessfully !")
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

    fun doneShowingSnackbar(){
        _showSnackbarEvent.value = false
    }

    private val _detailPerson = MutableLiveData<Int>()

    val detailPerson: LiveData<Int>
        get() = _detailPerson

    fun onPersonClicked(id: Int){
        //Timber.d("Person clicked !")
        _detailPerson.value = id
    }

    fun onPersonDetailed(){
        _detailPerson.value = null
    }

    fun onInputText(string: String){
        viewModelScope.launch {
            _personSearch.value = listRepository.peopleSearched(string)
        }
    }
    private val _favoritePosition = MutableLiveData<Int>()

    val favoritePosition :LiveData<Int>
        get() = _favoritePosition

    private val _favoriteResponse = MutableLiveData<Response<FavoriteNetworkObject>>()

    val favoriteResponse: LiveData<Response<FavoriteNetworkObject>>
        get() = _favoriteResponse

    private val _changePrefer = MutableLiveData(201)

    fun onFavoriteClicked(person: PersonModel, position: Int){
        //Timber.d("Favorite clicked !")
        viewModelScope.launch {
            if(!person.isFavorite || person.isFavorite == null){
                try {
                    val responseObject = listRepository.favoritePerson(person.id, _changePrefer.value!!)
                    if(_changePrefer.value == 201)
                        _changePrefer.value = 400
                    else
                        _changePrefer.value = 201
                    _favoriteResponse.value = responseObject
                    //Timber.d("Response code: ${responseObject.code()}")
                    if(responseObject.isSuccessful){
                        //Timber.d("Response code: \n${responseObject.code()}\nMessage: ${responseObject.body()?.message}")
                        when (listRepository.updateFavoriteDatabase(person.id, true)){
                            true -> {
                                _favoritePosition.value = position
                                val personUpdated = listRepository.getPerson(person.id)
                                //Timber.d("[${personUpdated?.isFavorite}] Person ${person.name} is favorited ! :D")
                            }
                            false ->{
                                //Timber.d("Fail to update person ${person.name}")
                            }
                        }
                    }
                    _eventNetworkError.value=false
                    _isNetworkErrorShown.value =false
                }
                catch (networkError: IOException){
                    _eventNetworkError.value=true
                }
            }
            else{
                when (listRepository.updateFavoriteDatabase(person.id, false)){
                    true -> {
                        _favoritePosition.value = position
                        val personUpdated = listRepository.getPerson(person.id)
                        //Timber.d("[${personUpdated?.isFavorite}] Person is not along favorited ! D:")
                    }
                    false -> {
                        //Timber.d("Fail to update person ${person.name}")
                    }
                }
            }
        }
    }

    fun afterFavoriteResponse(){
        _favoriteResponse.value = null
    }

    fun afterFavorite(){
        _favoritePosition.value = null
    }
}
