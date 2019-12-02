package com.example.starwarswiki.viewmodel

import android.app.Application
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.*
import com.example.starwarswiki.database.PersonDao
import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.network.FavoriteNetworkObject
import com.example.starwarswiki.network.NetworkObject
import com.example.starwarswiki.network.NetworkPerson
import com.example.starwarswiki.network.PersonNetworkService
import com.example.starwarswiki.repository.PersonListRepository
import com.example.starwarswiki.ui.PersonListFragment
import kotlinx.coroutines.*
import org.json.JSONObject
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

    private val _favoriteId = MutableLiveData<PersonModel>()

    val favoriteId:LiveData<PersonModel>
        get() = _favoriteId

    private val _favoriteView = MutableLiveData<View>()

    val favoriteView:LiveData<View>
        get() = _favoriteView

    fun onFavoriteClicked(person: PersonModel, view: View){
        _favoriteView.value = view
        viewModelScope.launch {
            if(!person.isFavorite){
                val responseObject = listRepository.favoritePerson(person.id)
                if(responseObject.isSuccessful){
                    Timber.d("Response code: \n${responseObject.code()}\nMessage: ${responseObject.body()?.message}")
                    when (listRepository.updateFavoriteDatabase(person.id, true)){
                        true -> {
                            val personUpdated = listRepository.getPerson(person.id)
                            Timber.d("[${personUpdated?.isFavorite}] Person ${person.name} is favorited ! :D")
                        }
                        false ->{
                            Timber.d("Fail to update person ${person.name}")
                        }
                    }
                }
                else{
                    val jsonObject = JSONObject(responseObject.errorBody()?.string())
                    val errorObject = FavoriteNetworkObject(null, null, error = jsonObject.getString("error"), errorMessage = jsonObject.getString("error_message"))
                    Timber.d("Response error: ${errorObject.error}\n${errorObject.errorMessage}!")
                }
            }

            else{
                when (listRepository.updateFavoriteDatabase(person.id, false)){
                    true -> {
                        val personUpdated = listRepository.getPerson(person.id)
                        Timber.d("[${personUpdated?.isFavorite}] Person is not along favorited ! D:")
                    }
                    false -> {
                        Timber.d("Fail to update person ${person.name}")
                    }
                }
            }
        }
    }
    fun onInputText(string: String){
        viewModelScope.launch {
            _personSearch.value = listRepository.peopleSearched(string)
        }
    }
}
