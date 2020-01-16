package br.com.starwarswiki.views.person


import android.content.Context
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.models.Person
import br.com.starwarswiki.views.ReactRenderableView
import trikita.anvil.Anvil

abstract class BasePeopleView(context: Context): ReactRenderableView(context) {

    protected var person: Person? = null

    fun person(person: Person) {
        this.person = person
        Anvil.render(this)
    }

    override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
        return newState.people?.firstOrNull { it.name == person?.name } !=
                oldState.people?.firstOrNull { it.name == person?.name }
    }

    override fun onChanged(state: AppState) {
        state.people?.firstOrNull { it.name == person?.name }?.let {
            this.person = it
            Anvil.render(this)
        }
    }
}