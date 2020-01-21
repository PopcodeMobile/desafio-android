package br.com.starwarswiki.reducers

import br.com.starwarswiki.actions.Actions
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.models.Person
import com.github.raulccabreu.redukt.actions.Reduce
import com.github.raulccabreu.redukt.reducers.BaseAnnotatedReducer

class PeopleReducer : BaseAnnotatedReducer<AppState>() {

    @Reduce(Actions.SAVE_PEOPLE)
    fun savePeople(state: AppState, people: Map<String, Person>): AppState {
        return state.copy(people = state.people.plus(people))
    }

    @Reduce(Actions.ADD_FAVORITE)
    fun addFavorite(state: AppState, name: String): AppState {
        val newPerson = state.people
            .values.firstOrNull { it.name == name }
            ?.copy(isFavorite = true) ?: return state
        return setPeople(state, newPerson)
    }

    @Reduce(Actions.REMOVE_FAVORITE)
    fun removeFavorite(state: AppState, name: String): AppState {
        val newPerson = state.people
            ?.values.firstOrNull { it.name == name }
            ?.copy(isFavorite = false) ?: return state
        return setPeople(state, newPerson)
    }

    private fun setPeople(state: AppState, newPerson: Person): AppState {
        val peopleMap = state.people.toMutableMap()

        peopleMap[newPerson.name] = newPerson

        val newPeople = peopleMap.toMap()

        return state.copy(people = LinkedHashMap(newPeople))
    }
}