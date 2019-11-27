package com.example.starwarswiki.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.starwarswiki.database.PersonDao
import com.example.starwarswiki.database.PersonRoomDatabase
import com.example.starwarswiki.database.asDomainModel
import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.network.PersonNetworkService
import com.example.starwarswiki.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class PersonListRepository(private val database: PersonDao){

    suspend fun refreshList(){
        withContext(Dispatchers.IO){
            Timber.d("Refreshing list...")
            var index = 1
            do {
                val objectRequest = PersonNetworkService.bruteRequest.getObject(index).await()
                database.insertAll(objectRequest.asDatabaseModel())
                index++
            }while (objectRequest.next != null)
        }
    }
    val personList: LiveData<List<PersonModel>> = Transformations.map(
        database.getAllPeople()
    ){
        it.asDomainModel()
    }

    suspend fun peopleSearched(searchText: String): List<PersonModel>?{
        return withContext(Dispatchers.IO){
            val peopleList= database.getSearch(searchText)?.asDomainModel()
            Timber.d("Size of filtered list: ${peopleList?.size}")
            peopleList
        }
    }
}
