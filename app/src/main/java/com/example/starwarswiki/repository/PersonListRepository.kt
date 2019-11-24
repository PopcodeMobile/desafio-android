package com.example.starwarswiki.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.starwarswiki.database.PersonRoomDatabase
import com.example.starwarswiki.database.asDomainModel
import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.network.PersonNetworkService
import com.example.starwarswiki.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class PersonListRepository(private val database: PersonRoomDatabase){

    suspend fun refreshList(){
        withContext(Dispatchers.IO){
            Timber.d("Refreshing list...")
            val objectRequest = PersonNetworkService.bruteRequest.getObject().await()
            database.personDao.insertAll(objectRequest.asDatabaseModel())
        }
    }
    val personList: LiveData<List<PersonModel>> = Transformations.map(
        database.personDao.getPersons()
    ){
        it.asDomainModel()
    }
}
