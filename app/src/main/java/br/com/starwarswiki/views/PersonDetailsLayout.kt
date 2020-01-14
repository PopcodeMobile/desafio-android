package br.com.starwarswiki.views

import android.content.Context
import android.util.Log
import android.widget.LinearLayout.VERTICAL
import br.com.starwarswiki.StarWarsApplication
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.models.Person
import br.com.starwarswiki.views.person.personDetailView
import trikita.anvil.Anvil
import trikita.anvil.BaseDSL.MATCH
import trikita.anvil.BaseDSL.size
import trikita.anvil.DSL.*

class PersonDetailsLayout(context: Context, name: String): ReactRenderableView(context){

    private var person: Person? = null
    private val name = name

    override fun view() {
        scrollView {
            size(MATCH, MATCH)

            linearLayout {
                size(MATCH, MATCH)
                orientation(VERTICAL)

                renderDetail()
            }
        }
    }

    private fun renderDetail() {
        getPerson(name)
        val person = person ?: return
        personDetailView { person(person) }
    }

    override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
        return newState != oldState
    }

    override fun onChanged(state: AppState) {
        Anvil.render()
    }

    private fun getPerson(name: String) {
        val state = StarWarsApplication.redukt.state
        person = state.people?.firstOrNull { it.name == name }
    }

}