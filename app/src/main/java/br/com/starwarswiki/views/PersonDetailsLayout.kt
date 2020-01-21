package br.com.starwarswiki.views

import android.content.Context
import android.widget.LinearLayout.VERTICAL
import br.com.starwarswiki.anvil.FrameLayoutComponent
import br.com.starwarswiki.models.Person
import br.com.starwarswiki.views.person.personDetailView
import trikita.anvil.BaseDSL.MATCH
import trikita.anvil.BaseDSL.size
import trikita.anvil.DSL.*

inline fun personDetailLayout(crossinline func: PersonDetailsLayout.() -> Unit) {
    dslAddView(func)
}

class PersonDetailsLayout(context: Context): FrameLayoutComponent(context){

    private var person: Person? = null

    override fun view() {
        val person = person ?: return
        scrollView {
            size(MATCH, MATCH)

            linearLayout {
                size(MATCH, MATCH)
                orientation(VERTICAL)

                renderDetail(person)
            }
        }
    }

    private fun renderDetail(person: Person) {
        personDetailView { person(person) }
    }

    fun person(person: Person) {
        this.person = person
    }
}