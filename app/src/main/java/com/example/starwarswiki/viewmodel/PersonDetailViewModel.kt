package com.example.starwarswiki.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarswiki.database.PersonDao
import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.network.PersonNetworkService
import com.example.starwarswiki.repository.PersonListRepository
import com.example.starwarswiki.util.getObjectId
import kotlinx.coroutines.*
import timber.log.Timber

class PersonDetailViewModel(
    private val dataSource: PersonDao,
    val id: Int)
    : ViewModel() {
    private val repositoryService = PersonListRepository(dataSource)
    private val viewModelJob = Job()
    private val uiCoroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private var _person = MutableLiveData<PersonModel?>()

    val person: LiveData<PersonModel?>
        get() = _person

    init {
        uiCoroutineScope.launch {
            _person.value = getPerson()
        }
    }

    private suspend fun getPerson():PersonModel?{
        return withContext(Dispatchers.IO) {
            val personRequest = dataSource.getPerson(id)
//            Timber.d("Person request: ${personRequest?.name}")
            personRequest?.let{
                requestPlanet(it.homeworld)
                requestSpecies(it.species)
            }
            personRequest
        }
    }

    private fun requestPlanet(planetUrl: String?){
        uiCoroutineScope.launch {
            planetUrl?.let{
                val iPlanet = getObjectId(it, "https://swapi.co/api/planets/")//it.substringAfter("https://swapi.co/api/planets/").removeSuffix("/").toInt()
                _planetName.value = getPlanet(iPlanet)
            }
        }
    }

    private fun requestSpecies(species: List<String>?){
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

    private suspend fun getPlanet(index: Int): String?{
        return withContext(Dispatchers.IO){
            val planetObject = PersonNetworkService.bruteRequest.getPlanet(index).await()
            "\nPlaneta natal: ${planetObject.name}"
        }
    }

    private val _speciesName = MutableLiveData<String>()

    val speciesName: LiveData<String>
        get() = _speciesName

    private suspend fun getSpecies(index: List<Int>?): String?{
       return withContext(Dispatchers.IO){
           var string = "\nEspécie(s): "
           index?.let{
                it.forEachIndexed { index, specieId ->
                    val specieObject = PersonNetworkService.bruteRequest.getSpecie(specieId).await()
                    if(index>0)
                        string += ", "
                    string += specieObject.name
                }
            }
           string
       }
    }

    fun updateFavoriteStatus(status: Boolean){
        viewModelScope.launch {
            repositoryService.updateFavoriteDatabase(id, status)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}