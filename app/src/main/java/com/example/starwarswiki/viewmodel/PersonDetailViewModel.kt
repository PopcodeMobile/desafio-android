package com.example.starwarswiki.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarswiki.database.PersonDao
import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.network.PersonNetworkService
import com.example.starwarswiki.network.PlanetNetworkObject
import com.example.starwarswiki.util.getObjectId
import kotlinx.coroutines.*
import timber.log.Timber

class PersonDetailViewModel(
    val dataSource: PersonDao,
    val id: Int)
    : ViewModel() {
    val database = dataSource
    val viewModelJob = Job()
    val uiCoroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private var _person = MutableLiveData<PersonModel?>()

    val person: LiveData<PersonModel?>
        get() = _person

    init {
        uiCoroutineScope.launch {
            _person.value = getPerson()
        }
    }

    suspend fun getPerson():PersonModel?{
        return withContext(Dispatchers.IO) {
            var personRequest = database.getPerson(id)
//            Timber.d("Person request: ${personRequest?.name}")
            personRequest?.let{
                requestPlanet(it.homeworld)
                requestSpecies(it.species)
            }
            personRequest
        }
    }

    fun requestPlanet(planetUrl: String?){
        uiCoroutineScope.launch {
            planetUrl?.let{
                val iPlanet = getObjectId(it, "https://swapi.co/api/planets/")//it.substringAfter("https://swapi.co/api/planets/").removeSuffix("/").toInt()
                _planetName.value = getPlanet(iPlanet)
            }
        }
    }

    fun requestSpecies(species: List<String>?){
        uiCoroutineScope.launch {
            species?.let{
                val indexSpecies = mutableListOf<Int>()
                it.forEach { s ->
                    val iSpecie = getObjectId(s, "https://swapi.co/api/species/")//s.substringAfter("https://swapi.co/api/species/").removeSuffix("/").toInt()
                    indexSpecies.add(iSpecie)
                }
                _speciesName.value = getSpecies(indexSpecies)
            }
        }
    }

    private val _planetName = MutableLiveData<String>()

    val planetName: LiveData<String>
        get() = _planetName

    suspend fun getPlanet(index: Int): String?{
        return withContext(Dispatchers.IO){
            val planetObject = PersonNetworkService.bruteRequest.getPlanet(index).await()
            planetObject.name
        }
    }

    private val _speciesName = MutableLiveData<MutableList<String>>()

    val speciesName: LiveData<MutableList<String>>
        get() = _speciesName

    suspend fun getSpecies(index: List<Int>?): MutableList<String>?{
       return withContext(Dispatchers.IO){
           var speciesList = mutableListOf<String>()
           index?.let{
                it.forEach {
                    val specieObject = PersonNetworkService.bruteRequest.getSpecie(it).await()
                    speciesList.add(specieObject.name)
                }
            }
           speciesList
       }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}