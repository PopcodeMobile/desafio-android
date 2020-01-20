package br.com.starwarswiki.activities

import android.view.View
import br.com.starwarswiki.actions.ActionCreator
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.views.PeopleLayout

class MainActivity : ReactiveActivity() {
    override fun render(): View {
        return PeopleLayout(this)
    }

    override fun initialState() {
        ActionCreator.loadDatabase()
    }

    override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
        return newState.stateStarted != oldState.stateStarted ||
                newState.synced() != oldState.synced()
    }

    override fun onChanged(state: AppState) {
        if (state.stateStarted && state.synced() == false) {
            syncContent()
        }
    }

    private fun syncContent() {
        ActionCreator.syncPeople()
        ActionCreator.syncPlanets()
        ActionCreator.syncSpecies()
    }
}
