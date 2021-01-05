package com.github.weslleystos.features.list.services

import androidx.lifecycle.MutableLiveData
import com.github.weslleystos.App
import com.github.weslleystos.features.list.repository.remote.IPeopleListRepository
import com.github.weslleystos.features.list.repository.remote.Response
import com.github.weslleystos.shared.entities.People
import com.github.weslleystos.shared.services.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback


class PeopleListService {
    private val peoplesDao = App.database.peoplesDao()
    private val peoplesService = RetrofitService().create<IPeopleListRepository>()

    fun getAll(page: Int, peoplesLiveData: MutableLiveData<Pair<Boolean, List<People>?>>) {
        val peoples = peoplesDao.getAll(page * 10)
        if (peoples.isEmpty()) {
            val service = peoplesService.getAll(page + 1)
            service.enqueue(object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    when {
                        response.isSuccessful -> {
                            val results = response.body()!!.results
                            peoplesLiveData.postValue(Pair(true, results))
                            persistInDatabase(parseData(results))
                        }
                        else -> peoplesLiveData.postValue(Pair(false, null))
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    peoplesLiveData.postValue(Pair(false, null))
                }
            })
        } else {
            peoplesLiveData.postValue(Pair(true, peoples))
        }

    }

    private fun parseData(results: List<People>): List<People> {
        results.forEach { people ->
            if (people.species?.size != 0) {
                people.specie = people.species?.get(0)
            }
        }
        return results
    }

    private fun persistInDatabase(results: List<People>) {
        GlobalScope.launch(Dispatchers.IO) {
            peoplesDao.insertAll(peoples = results.toTypedArray())
        }
    }
}