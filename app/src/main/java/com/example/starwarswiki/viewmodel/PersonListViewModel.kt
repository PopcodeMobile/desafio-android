package com.example.starwarswiki.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starwarswiki.network.NetworkObject
import com.example.starwarswiki.network.NetworkPerson
import com.example.starwarswiki.network.PersonNetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonListViewModel(application: Application) : ViewModel() {

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
        PersonNetworkService.personList.getPersonList().enqueue(
            object: Callback<NetworkObject>{
                override fun onFailure(call: Call<NetworkObject>, t: Throwable) {
                    _response.value = "Failure: "+ t.message
                }

                override fun onResponse(call: Call<NetworkObject>, response: Response<NetworkObject>) {
                    _response.value = "Sucess: ${response.body()?.count} items retrieved !"
                }

            }
        )
    }

    fun onNetworkErrorShown(){
        _isNetworkErrorShown.value = true
    }
}
