package br.com.starwarswiki.reducers

import br.com.starwarswiki.actions.Actions
import br.com.starwarswiki.models.AppState
import com.github.raulccabreu.redukt.actions.Reduce
import com.github.raulccabreu.redukt.reducers.BaseAnnotatedReducer

class DatabaseReducer : BaseAnnotatedReducer<AppState>() {

    @Reduce(Actions.LOADED_DATABASE)
    fun loadedDatabase(state: AppState, payload: AppState): AppState {
        return state.copy(
            people = LinkedHashMap(payload.people),
            planets = LinkedHashMap(payload.planets),
            species = LinkedHashMap(payload.species))
    }

}