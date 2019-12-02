package com.example.starwarswiki.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.starwarswiki.database.PersonDao
import com.example.starwarswiki.database.PersonRoomDatabase
import com.example.starwarswiki.database.asDomainModel
import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception

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

    suspend fun getPerson(id: Int): PersonModel?{
        return withContext(Dispatchers.IO){
            val person= database.getPerson(id)
            Timber.d("Found person: ${person?.name}")
            person
        }
    }

    suspend fun favoritePerson(id: Int): Response<FavoriteNetworkObject> {
        return withContext(Dispatchers.IO){
            val favoriteResponse = FavoriteRetrofit.retrofit.postFavorite(201 ,id = id).await()
            favoriteResponse
        }
    }
    suspend fun updateFavoriteDatabase(id: Int, status: Boolean): Boolean{
        return withContext(Dispatchers.IO){
            try {
                database.updateFavorite(id, status)
                true
            }
            catch (e: Exception){
                false
            }
        }
    }
}
