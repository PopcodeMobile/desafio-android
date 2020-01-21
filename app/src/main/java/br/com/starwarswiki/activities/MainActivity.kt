package br.com.starwarswiki.activities

import br.com.starwarswiki.actions.ActionCreator
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.views.peopleLayout

class MainActivity : ReactiveActivity() {
    override fun initialState() {
        syncContent()
    }

    override fun content() {
        peopleLayout {  }
    }

    override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
        return newState.syncRunning != oldState.syncRunning && !newState.syncRunning
    }

    override fun onChanged(state: AppState) {

    }

    private fun syncContent() {
        ActionCreator.syncPeople()
        ActionCreator.syncPlanets()
        ActionCreator.syncSpecies()
    }
}
