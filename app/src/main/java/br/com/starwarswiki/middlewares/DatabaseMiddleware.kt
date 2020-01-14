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
        val people = ObjectBox.boxStore?.boxFor(Person::class.java)?.all
        val planets = ObjectBox.boxStore?.boxFor(Planet::class.java)?.all
        val species = ObjectBox.boxStore?.boxFor(Specie::class.java)?.all

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
        val payload = action.payload as? ServerResponse<Person> ?: return
        ObjectBox.boxStore?.boxFor(Person::class.java)?.put(payload.results)
    }

    @AfterAction(Actions.SAVE_PLANETS)
    fun savePlanets(state: AppState, action: Action<*>) {
        val payload = action.payload as? ServerResponse<Planet> ?: return
        ObjectBox.boxStore?.boxFor(Planet::class.java)?.put(payload.results)
    }

    @AfterAction(Actions.SAVE_SPECIES)
    fun saveSpecies(state: AppState, action: Action<*>) {
        val payload = action.payload as? ServerResponse<Specie> ?: return
        ObjectBox.boxStore?.boxFor(Specie::class.java)?.put(payload.results)
    }
}