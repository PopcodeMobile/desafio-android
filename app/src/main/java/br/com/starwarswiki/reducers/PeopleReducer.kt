package br.com.starwarswiki.reducers

import br.com.starwarswiki.actions.Actions
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.models.Person
import br.com.starwarswiki.models.ServerResponse
import com.github.raulccabreu.redukt.actions.Reduce
import com.github.raulccabreu.redukt.reducers.BaseAnnotatedReducer

class PeopleReducer : BaseAnnotatedReducer<AppState>() {

    @Reduce(Actions.SAVE_PEOPLE)
    fun savePeople(state: AppState, payload: ServerResponse<Person>): AppState {
        return state.copy(people = payload.results)
    }
}