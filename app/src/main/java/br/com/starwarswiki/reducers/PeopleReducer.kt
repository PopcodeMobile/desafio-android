package br.com.starwarswiki.reducers

import br.com.starwarswiki.actions.Actions
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.models.Person
import com.github.raulccabreu.redukt.actions.Reduce
import com.github.raulccabreu.redukt.reducers.BaseAnnotatedReducer

class PeopleReducer : BaseAnnotatedReducer<AppState>() {

    @Reduce(Actions.SAVE_PEOPLE)
    fun savePeople(state: AppState, payload: List<Person>): AppState {
        return state.copy(people = payload)
    }

    @Reduce(Actions.ADD_FAVORITE)
    fun addFavorite(state: AppState, name: String): AppState {
        val newPerson = state.people
            ?.firstOrNull { it.name == name }
            ?.copy(isFavorite = true) ?: return state
        return setPeople(state, newPerson)
    }

    @Reduce(Actions.REMOVE_FAVORITE)
    fun removeFavorite(state: AppState, name: String): AppState {
        val newPerson = state.people
            ?.firstOrNull { it.name == name }
            ?.copy(isFavorite = false) ?: return state
        return setPeople(state, newPerson)
    }

    private fun setPeople(state: AppState, newPerson: Person): AppState {
        val peopleMap = state.people
            ?.map { it.name to it }
            ?.toMap()
            ?.toMutableMap() ?: return state

        peopleMap[newPerson.name] = newPerson

        val newPeople = peopleMap.values.toList()

        return state.copy(people = newPeople)
    }
}