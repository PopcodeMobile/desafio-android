package br.com.starwarswiki.actions

import android.service.voice.AlwaysOnHotwordDetector
import br.com.starwarswiki.StarWarsApplication
import br.com.starwarswiki.actions.Actions.ADD_FAVORITE
import br.com.starwarswiki.actions.Actions.FILTER_BY_FAVORITE
import br.com.starwarswiki.actions.Actions.LOADED_DATABASE
import br.com.starwarswiki.actions.Actions.LOAD_DATABASE
import br.com.starwarswiki.actions.Actions.REMOVE_FAVORITE
import br.com.starwarswiki.actions.Actions.RESULT
import br.com.starwarswiki.actions.Actions.SAVE_PEOPLE
import br.com.starwarswiki.actions.Actions.SAVE_PLANETS
import br.com.starwarswiki.actions.Actions.SAVE_SPECIES
import br.com.starwarswiki.actions.Actions.SEARCH_BY_NAME
import br.com.starwarswiki.actions.Actions.SYNC_PEOPLE
import br.com.starwarswiki.actions.Actions.SYNC_PLANETS
import br.com.starwarswiki.actions.Actions.SYNC_SPECIES
import br.com.starwarswiki.models.*
import com.github.raulccabreu.redukt.actions.Action

object ActionCreator {

    fun loadDatabase() {
        asyncDispatch(Action<Any>(LOAD_DATABASE))
    }

    fun loadedDatabase(state: AppState) {
        asyncDispatch(Action<Any>(LOADED_DATABASE, state))
    }

    fun syncPeople() {
        asyncDispatch(Action<Any>(SYNC_PEOPLE))
    }

    fun syncPlanets() {
        asyncDispatch(Action<Any>(SYNC_PLANETS))
    }

    fun syncSpecies() {
        asyncDispatch(Action<Any>(SYNC_SPECIES))
    }

    fun saveResponsePerson(response: List<Person>) {
        asyncDispatch(Action(SAVE_PEOPLE, response))
    }

    fun saveResponsePlanet(response: List<Planet>) {
        asyncDispatch(Action(SAVE_PLANETS, response))
    }

    fun saveResponseSpecie(response: List<Specie>) {
        asyncDispatch(Action(SAVE_SPECIES, response))
    }

    fun addFavorite(name: String) {
        asyncDispatch(Action(ADD_FAVORITE, name))
    }

    fun removeFavorite(name: String) {
        asyncDispatch(Action(REMOVE_FAVORITE, name))
    }

    private fun asyncDispatch(action: Action<*>) {
        StarWarsApplication.redukt.dispatch(action, true)
    }

    fun filterByFavorite() {
        asyncDispatch(Action(FILTER_BY_FAVORITE, true))
    }

    fun searchByName(payload: String) {
        asyncDispatch(Action(SEARCH_BY_NAME, payload))
    }

    fun result(state: List<Person>) {
        asyncDispatch(Action(RESULT, state))
    }
}