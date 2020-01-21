package br.com.starwarswiki.anvil

import android.content.Context
import br.com.starwarswiki.StarWarsApplication
import br.com.starwarswiki.models.AppState
import com.github.raulccabreu.redukt.states.StateListener

@Suppress("EmptyFunctionBlock")
abstract class ReactiveFrameComponent(context: Context) : FrameLayoutComponent(context),
    StateListener<AppState>, AnvilRenderable {

    private var stateChange = StateComponentHelper()
    private var anvilRenderListener: AnvilRenderListener? = null
    private var state: AppState? = null

    init {
        initialState()
        registered()
    }

    protected open fun initialState() {
        stateChange.onRegisterOnStateChange(this)
    }

    override fun setAnvilRenderListener(listener: AnvilRenderListener) {
        anvilRenderListener = listener
    }

    protected fun onAnvilRendered() {
        anvilRenderListener?.onAnvilRendered()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        stateChange.onFocusChanged(hasWindowFocus, this,
            this, state, StarWarsApplication.redukt.state)
        state = StarWarsApplication.redukt.state

        if (hasWindowFocus) registered()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stateChange.onUnregisterOnStateChange(this)
        unregistered()
    }

    override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
        return newState != state
    }

    open fun registered() { }

    open fun unregistered() { }
}