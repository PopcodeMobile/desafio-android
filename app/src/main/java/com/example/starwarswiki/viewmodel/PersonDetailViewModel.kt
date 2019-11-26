package com.example.starwarswiki.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.starwarswiki.database.PersonDao
import com.example.starwarswiki.domain.PersonModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

class PersonDetailViewModel(
    val dataSource: PersonDao,
    val url: String)
    : ViewModel() {
    val database = dataSource
    val viewModelJob = Job()

    private val person: LiveData<PersonModel>

    fun getPerson() = person

    init {
        person = database.getLivedataPerson(url)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}