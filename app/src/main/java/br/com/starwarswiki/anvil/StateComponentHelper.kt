package br.com.starwarswiki.anvil

import br.com.starwarswiki.StarWarsApplication
import br.com.starwarswiki.models.AppState
import com.github.raulccabreu.redukt.states.StateListener

class StateComponentHelper {

    private var isRegisteredOnStateChange = false

    fun onFocusChanged(hasFocus: Boolean, render: RenderListener, listener: StateListener<AppState>,
                       actualState: AppState?, newState: AppState) {
        if (hasFocus) {
            onRegisterOnStateChange(listener)
            if (actualState == null || listener.hasChanged(newState, actualState)) {
                listener.onChanged(newState)
                render.render()
            }
        }
    }

    fun onRegisterOnStateChange(listener: StateListener<AppState>) {
        if (isRegisteredOnStateChange) return

        isRegisteredOnStateChange = true
        StarWarsApplication.redukt.listeners.add(listener)
    }

    fun onUnregisterOnStateChange(listener: StateListener<AppState>) {
        isRegisteredOnStateChange = false
        StarWarsApplication.redukt.listeners.remove(listener)
    }
}