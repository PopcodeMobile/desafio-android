package br.com.example.starwars.presentation.ui.peopledetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.example.starwars.domain.entities.People
import br.com.example.starwars.domain.entities.Planet
import br.com.example.starwars.domain.entities.Specie
import br.com.example.starwars.domain.usecase.FavoritePerson
import br.com.example.starwars.domain.usecase.GetPlanet
import br.com.example.starwars.domain.usecase.GetSpecie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleDetailViewModel @Inject constructor(
    private val getPlanet: GetPlanet,
    private val getSpecie: GetSpecie,
    private val favoritePerson: FavoritePerson
) : ViewModel() {

    val planet: LiveData<Planet> get() = _planet
    private val _planet: MutableLiveData<Planet> = MutableLiveData()

    val specie: LiveData<Specie> get() = _specie
    private val _specie: MutableLiveData<Specie> = MutableLiveData()

    val loading: LiveData<Boolean> get() = _loading
    private val _loading: MutableLiveData<Boolean> = MutableLiveData()

    internal fun getPlanetAndSpecie(homeWorld: String, species: List<String>) {
        viewModelScope.launch {
            awaitAll(
                async { _planet.value = getPlanet.execute(homeWorld) },
                async {
                    if (species.isNotEmpty())
                        _specie.value = getSpecie.execute(species[0])
                }
            )
            _loading.value = false
        }
    }

    internal fun favoritePerson(people: People) {
        viewModelScope.launch {
            people.id?.let { favoritePerson.execute(people.favorite, it) }
        }
    }
}