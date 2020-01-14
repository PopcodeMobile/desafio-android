package br.com.starwarswiki.views.person


import android.content.Context
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.models.Person
import br.com.starwarswiki.models.ServerResponse
import br.com.starwarswiki.views.ReactRenderableView
import trikita.anvil.Anvil

abstract class BasePeopleView(context: Context): ReactRenderableView(context) {

    protected var person: Person? = null

    fun person(person: Person) {
        this.person = person
        Anvil.render(this)
    }

    override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
        return newState.people != oldState.people
    }

    override fun onChanged(state: AppState) {
        Anvil.render(this)
    }
}