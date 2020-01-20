package br.com.starwarswiki.reducers

import br.com.starwarswiki.actions.Actions
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.models.ServerResponse
import br.com.starwarswiki.models.Specie
import com.github.raulccabreu.redukt.actions.Reduce
import com.github.raulccabreu.redukt.reducers.BaseAnnotatedReducer

class SpeciesReducer : BaseAnnotatedReducer<AppState>() {

    @Reduce(Actions.SAVE_SPECIES)
    fun saveSpecies(state: AppState, payload: List<Specie>): AppState {
        return state.copy(species = payload)
    }
}