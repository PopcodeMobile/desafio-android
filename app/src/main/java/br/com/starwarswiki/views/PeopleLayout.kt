package br.com.starwarswiki.views

import android.content.Context
import android.database.DataSetObserver
import android.widget.LinearLayout.HORIZONTAL
import br.com.starwarswiki.activities.PersonDetailActivity
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.models.Person
import br.com.starwarswiki.views.person.personSummaryView
import br.com.starwarswiki.R
import br.com.starwarswiki.StarWarsApplication
import br.com.starwarswiki.anvil.ReactiveFrameComponent
import trikita.anvil.BaseDSL
import trikita.anvil.BaseDSL.MATCH
import trikita.anvil.BaseDSL.size
import trikita.anvil.DSL.*
import trikita.anvil.RenderableAdapter

inline fun peopleLayout(crossinline func: PeopleLayout.() -> Unit) {
    dslAddView(func)
}

class PeopleLayout(context: Context): ReactiveFrameComponent(context){

    private var people = listOf<Person>()
    private var name: String? = null

    override fun view() {
        val adapter = RenderableAdapter.withItems(people) { _, person ->
            personSummaryView {
                person(person)
            }
        }

        adapter.registerDataSetObserver(object : DataSetObserver() {
            override fun onChanged() {
                super.onChanged()
                render()
            }
        })

        focusableInTouchMode(true)

        relativeLayout {
            listView {
                BaseDSL.above(1)
                size(MATCH, MATCH)
                adapter(adapter)
                onItemClick { _, _, pos, _ ->
                    PersonDetailActivity.open(context, people[pos].name)
                }
            }

            renderFindComponent()
        }
    }

    fun renderFindComponent() {
        linearLayout {
            id(1)
            alignParentBottom()
            orientation(HORIZONTAL)
            size(MATCH, BaseDSL.dip(45))
            weightSum(1f)

            val state = StarWarsApplication.redukt.state

            editText {
                BaseDSL.weight(.8f)
                size(0, MATCH)
                BaseDSL.margin(10, 0, 10, 0)
                BaseDSL.textSize(20.0f)
                onTextChanged { name = it.toString() }
                enabled(true)
            }

            textView {
                BaseDSL.weight(.2f)
                size(0, MATCH)
                backgroundColor(R.color.colorPrimaryDark)
                BaseDSL.margin(0, 0, 10, 0)
                gravity(BaseDSL.CENTER)
                textColor(R.color.colorAccent)
                BaseDSL.textSize(20.0f)
                text("Pesquisar")
                onClick {
                    people = state.search(state, name) ?: state.people.values.toList()
                }
            }
        }
    }

    override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
        return newState.people != oldState.people
    }

    override fun onChanged(state: AppState) {
        people = state.people.values.toList()
        render()
    }

}