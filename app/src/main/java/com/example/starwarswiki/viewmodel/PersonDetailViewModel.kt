package com.example.starwarswiki.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarswiki.database.PersonDao
import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.network.PersonNetworkService
import com.example.starwarswiki.network.PlanetNetworkObject
import kotlinx.coroutines.*
import timber.log.Timber

class PersonDetailViewModel(
    val dataSource: PersonDao,
    val url: String)
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
            var personRequest = database.getPerson(url)
            Timber.d("Person request: ${personRequest?.name}")
            personRequest?.let{
                onInit(it.homeworld, it.species)
            }
            personRequest
        }
    }

    fun onInit(planetUrl: String?, species: List<String>?){
        Timber.d("onInit(${planetUrl}) called !")
        uiCoroutineScope.launch {
            planetUrl?.let{
                val iPlanet = it.substringAfter("https://swapi.co/api/planets/").removeSuffix("/").toInt()
                Timber.d("${iPlanet}")
                _planetName.value = getPlanet(iPlanet)
            }
//            species?.let{
//                val indexSpecies = mutableListOf<Int>()
//                species.forEach { s ->
//                    val iSpecie = s.substringAfter("https://swapi.co/api/species/").removeSuffix("/").toInt()
//                    indexSpecies.add(iSpecie)
//                }
//                getSpecies(indexSpecies)
//            }
        }

    }

    private val _planetName = MutableLiveData<String>()

    val planetName: LiveData<String>
        get() = _planetName

    suspend fun getPlanet(index: Int): String?{
        return withContext(Dispatchers.IO){
            val planetObject = PersonNetworkService.bruteRequest.getPlanet(index).await()
            Timber.d("getPlanet = ${planetObject.name}")
            planetObject.name
        }
    }

    private val _speciesName = MutableLiveData<MutableList<String>>()

    val speciesName: LiveData<MutableList<String>>
        get() = _speciesName

    suspend fun getSpecies(index: List<Int>?){
        withContext(Dispatchers.IO){
            index?.let{
                index.forEach {
                    val specieObject = PersonNetworkService.bruteRequest.getSpecie(it).await()
                    _speciesName.value?.add(specieObject.name)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}