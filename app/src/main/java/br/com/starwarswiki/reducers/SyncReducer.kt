package br.com.starwarswiki.reducers

import br.com.starwarswiki.actions.Actions
import br.com.starwarswiki.models.AppState
import com.github.raulccabreu.redukt.actions.Reduce
import com.github.raulccabreu.redukt.reducers.BaseAnnotatedReducer

class SyncReducer : BaseAnnotatedReducer<AppState>() {

    @Reduce(Actions.SYNC_STARTED)
    fun updateSync(state: AppState, status: Boolean): AppState {
        return state.copy(syncRunning = status)
    }
}