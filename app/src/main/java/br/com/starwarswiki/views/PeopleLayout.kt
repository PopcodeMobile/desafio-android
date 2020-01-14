package br.com.starwarswiki.views

import android.content.Context
import android.content.Intent
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.LinearLayout.VERTICAL
import br.com.starwarswiki.activities.PersonDetailActivity
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.models.Person
import br.com.starwarswiki.views.person.personDetailView
import br.com.starwarswiki.views.person.personSummaryView
import trikita.anvil.Anvil
import trikita.anvil.BaseDSL.MATCH
import trikita.anvil.BaseDSL.size
import trikita.anvil.DSL.*

class PeopleLayout(context: Context): ReactRenderableView(context){

    private var people = listOf<Person>()

    override fun view() {
        scrollView {
            size(MATCH, MATCH)

            linearLayout {
                size(MATCH, MATCH)
                orientation(VERTICAL)

                renderSummary()
            }
        }
    }

    private fun renderSummary() {
        people.forEach { person ->
            personSummaryView {
                person(person)

                onClick {
                    PersonDetailActivity.open(context, person.name)
                }
            }
        }
    }

    override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
        return newState != oldState
    }

    override fun onChanged(state: AppState) {
        people = state.people ?: listOf()
        Anvil.render()
    }

}