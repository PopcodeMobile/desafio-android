package br.com.starwarswiki.extensions

import android.view.View
import trikita.anvil.Anvil

inline fun onClick(crossinline func: (v: View) -> Unit) {
    val view: View = Anvil.currentView()
    view.setOnClickListener {
        func.invoke(it)
    }
}