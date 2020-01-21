package br.com.starwarswiki.middlewares

import br.com.starwarswiki.actions.ActionCreator
import br.com.starwarswiki.actions.Actions
import br.com.starwarswiki.database.ObjectBox
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.models.Person
import br.com.starwarswiki.models.Planet
import br.com.starwarswiki.models.Specie
import com.github.raulccabreu.redukt.actions.Action
import com.github.raulccabreu.redukt.middlewares.AfterAction
import com.github.raulccabreu.redukt.middlewares.BaseAnnotatedMiddleware
import com.github.raulccabreu.redukt.middlewares.BeforeAction

class DatabaseMiddleware : BaseAnnotatedMiddleware<AppState>() {

    @BeforeAction(Actions.LOAD_DATABASE)
    fun syncSWAPI(state: AppState, action: Action<*>) {
        val people = ObjectBox.boxStore?.boxFor(Person::class.java)?.all ?: listOf()
        val planets = ObjectBox.boxStore?.boxFor(Planet::class.java)?.all ?: listOf()
        val species = ObjectBox.boxStore?.boxFor(Specie::class.java)?.all ?: listOf()

        val mapPeople = people.map { it.name to it }.toMap()
        val mapPlanets = planets.map { it.name to it }.toMap()
        val mapSpecies = species.map { it.name to it }.toMap()

        ActionCreator.loadedDatabase(
            AppState(
                people = mapPeople,
                planets = mapPlanets,
                species = mapSpecies
            )
        )
    }

    @AfterAction(Actions.SAVE_PEOPLE)
    fun savePeople(state: AppState, action: Action<*>) {
        val payload = action.payload as? Map<String, Person> ?: return
        ObjectBox.boxStore?.boxFor(Person::class.java)?.put(payload.values)
    }

    @AfterAction(Actions.SAVE_PLANETS)
    fun savePlanets(state: AppState, action: Action<*>) {
        val payload = action.payload as? Map<String, Planet> ?: return
        ObjectBox.boxStore?.boxFor(Planet::class.java)?.put(payload.values)
    }

    @AfterAction(Actions.SAVE_SPECIES)
    fun saveSpecies(state: AppState, action: Action<*>) {
        val payload = action.payload as? Map<String, Specie> ?: return
        ObjectBox.boxStore?.boxFor(Specie::class.java)?.put(payload.values)
    }

    @AfterAction(Actions.ADD_FAVORITE)
    fun addFavorite(state: AppState, action: Action<*>) {
        ObjectBox.boxStore?.boxFor(Person::class.java)?.put(state.people.values)
    }

    @AfterAction(Actions.REMOVE_FAVORITE)
    fun removeFavorite(state: AppState, action: Action<*>) {
        ObjectBox.boxStore?.boxFor(Person::class.java)?.put(state.people.values)
    }
}