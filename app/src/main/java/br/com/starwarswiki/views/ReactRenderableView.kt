package br.com.starwarswiki.views

import android.content.Context
import android.widget.FrameLayout
import br.com.starwarswiki.StarWarsApplication
import br.com.starwarswiki.models.AppState
import com.github.raulccabreu.redukt.states.StateListener
import trikita.anvil.Anvil

abstract class ReactRenderableView(context: Context): FrameLayout(context),
    Anvil.Renderable, StateListener<AppState> {

    private var isRegisteredOnStateChange = false
    private var state: AppState? = null

    public override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Anvil.mount(this, this)
        onRegisterOnStateChange(this)
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Anvil.unmount(this)
        onUnregisterOnStateChange(this)
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        onFocusChanged(hasWindowFocus, state, StarWarsApplication.redukt.state)
    }

    private fun onFocusChanged(hasFocus: Boolean, actualState: AppState?, newState: AppState) {
        if (hasFocus) {
            onRegisterOnStateChange(this)
            if (actualState == null || this.hasChanged(newState, actualState)) {
                this.onChanged(newState)
                Anvil.render(this)
            }
        } else {
            onUnregisterOnStateChange(this)
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

    abstract override fun view()
}