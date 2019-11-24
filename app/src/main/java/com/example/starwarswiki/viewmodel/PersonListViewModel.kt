package com.example.starwarswiki.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starwarswiki.network.PersonNetworkService
import com.example.starwarswiki.network.PersonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonListViewModel : ViewModel() {
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    init {
        getPersonListProperties()
    }

    private fun getPersonListProperties(){
        PersonNetworkService.personList.getPersonList().enqueue(
            object: Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {
                    _response.value = "Failure: "+ t.message
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    _response.value = "Sucess: "+ response.body()
                }

            }
        )
    }
}
