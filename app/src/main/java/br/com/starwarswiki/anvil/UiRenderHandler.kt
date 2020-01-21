package br.com.starwarswiki.anvil

import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import trikita.anvil.Anvil

class UiRenderHandler {

    private var uiHandler: Handler? = null

    fun <T: ViewGroup> render(view: T) {
        synchronized(Anvil::class.java) {
            if (uiHandler == null) {
                uiHandler = Handler(Looper.getMainLooper())
            }
        }

        uiHandler?.let {
            LayoutComponentHelper.render(view, it) {
                Anvil.render(view)
            }
        }
    }

}