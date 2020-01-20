package br.com.starwarswiki.views

import android.content.Context
import br.com.starwarswiki.activities.PersonDetailActivity
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.models.Person
import br.com.starwarswiki.views.person.personSummaryView
import br.com.starwarswiki.R
import br.com.starwarswiki.actions.ActionCreator
import trikita.anvil.Anvil
import trikita.anvil.BaseDSL
import trikita.anvil.BaseDSL.MATCH
import trikita.anvil.BaseDSL.size
import trikita.anvil.DSL.*
import trikita.anvil.RenderableAdapter

class PeopleLayout(context: Context): ReactRenderableView(context){

    private var people = listOf<Person>()

    override fun view() {
        val adapter = RenderableAdapter.withItems(people) { _, person ->
            frameLayout {
                personSummaryView {
                    person(person)
                }
            }
        }

        relativeLayout {
            listView {
                id(1)
                BaseDSL.above(2)
                size(MATCH, MATCH)
                adapter(adapter)
                onItemClick { _, _, pos, _ ->
                    PersonDetailActivity.open(context, people[pos].name)
                }
            }

            renderFilterButton()
        }
    }

    fun renderFilterButton() {
        textView {
            id(2)
            BaseDSL.alignParentBottom()
            size(MATCH, BaseDSL.dip(45))
            BaseDSL.margin(10)
            backgroundColor(R.color.colorPrimaryDark)
            gravity(BaseDSL.CENTER)
            textColor(R.color.colorAccent)
            BaseDSL.textSize(20.0f)
            text("Filtrar Favoritos")
            onClick {
                ActionCreator.filterByFavorite()

            }

        }
    }

    override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
        return newState.people?.map { it.name } != oldState.people?.map { it.name }
    }

    override fun onChanged(state: AppState) {
        people = state.people ?: listOf()
        Anvil.render()
    }

}