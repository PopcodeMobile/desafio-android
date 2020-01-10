package br.com.starwarswiki.actions

import br.com.starwarswiki.StarWarsApplication
import br.com.starwarswiki.actions.Actions.SAVE_PEOPLE
import br.com.starwarswiki.actions.Actions.SAVE_PLANETS
import br.com.starwarswiki.actions.Actions.SAVE_SPECIES
import br.com.starwarswiki.actions.Actions.SYNC_PEOPLE
import br.com.starwarswiki.actions.Actions.SYNC_PLANETS
import br.com.starwarswiki.actions.Actions.SYNC_SPECIES
import br.com.starwarswiki.models.Person
import br.com.starwarswiki.models.Planet
import br.com.starwarswiki.models.ServerResponse
import br.com.starwarswiki.models.Specie
import com.github.raulccabreu.redukt.actions.Action

object ActionCreator {

    fun syncPeope() {
        asyncDispatch(Action<Any>(SYNC_PEOPLE))
    }

    fun syncPlanets() {
        asyncDispatch(Action<Any>(SYNC_PLANETS))
    }

    fun syncSpecies() {
        asyncDispatch(Action<Any>(SYNC_SPECIES))
    }

    fun saveResponsePerson(response: ServerResponse<Person>) {
        asyncDispatch(Action(SAVE_PEOPLE, response))
    }

    fun saveResponsePlanet(response: ServerResponse<Planet>) {
        asyncDispatch(Action(SAVE_PLANETS, response))
    }

    fun saveResponseSpecie(response: ServerResponse<Specie>) {
        asyncDispatch(Action(SAVE_SPECIES, response))
    }

    private fun asyncDispatch(action: Action<*>) {
        StarWarsApplication.redukt.dispatch(action, true)
    }
}