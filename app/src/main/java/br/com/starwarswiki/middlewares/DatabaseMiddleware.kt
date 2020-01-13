package br.com.starwarswiki.middlewares

import br.com.starwarswiki.actions.ActionCreator
import br.com.starwarswiki.actions.Actions
import br.com.starwarswiki.database.ObjectBox
import br.com.starwarswiki.models.*
import com.github.raulccabreu.redukt.actions.Action
import com.github.raulccabreu.redukt.middlewares.AfterAction
import com.github.raulccabreu.redukt.middlewares.BaseAnnotatedMiddleware
import com.github.raulccabreu.redukt.middlewares.BeforeAction

class DatabaseMiddleware : BaseAnnotatedMiddleware<AppState>() {

    @BeforeAction(Actions.LOAD_DATABASE)
    fun syncSWAPI(state: AppState, action: Action<*>) {
        val people = ObjectBox.boxStore?.boxFor(ServerResponse::class.java)?.get(1)
        val planets = ObjectBox.boxStore?.boxFor(ServerResponse::class.java)?.get(1)
        val species = ObjectBox.boxStore?.boxFor(ServerResponse::class.java)?.get(1)

        people as ServerResponse<Person>
        planets as ServerResponse<Planet>
        species as ServerResponse<Specie>

        ActionCreator.loadedDatabase(
            AppState(
                people = people,
                planets = planets,
                species = species
            )
        )
    }

    @AfterAction(Actions.SAVE_PEOPLE)
    fun savePeople(state: AppState, action: Action<*>) {
        val payload = action.payload ?: return
        payload as ServerResponse<Person>
        ObjectBox.boxStore?.boxFor(ServerResponse::class.java)?.put(payload)
    }

    @AfterAction(Actions.SAVE_PLANETS)
    fun savePlanets(state: AppState, action: Action<*>) {
        val payload = action.payload ?: return
        payload as ServerResponse<Planet>
        ObjectBox.boxStore?.boxFor(ServerResponse::class.java)?.put(payload)
    }

    @AfterAction(Actions.SAVE_SPECIES)
    fun saveSpecies(state: AppState, action: Action<*>) {
        val payload = action.payload ?: return
        payload as ServerResponse<Specie>
        ObjectBox.boxStore?.boxFor(ServerResponse::class.java)?.put(payload)
    }
}