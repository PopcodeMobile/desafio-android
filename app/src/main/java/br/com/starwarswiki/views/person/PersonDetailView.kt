package br.com.starwarswiki.views.person

import android.content.Context
import br.com.starwarswiki.R
import br.com.starwarswiki.StarWarsApplication
import br.com.starwarswiki.views.cardLayout
import br.com.starwarswiki.views.dslAddView

inline fun personDetailView(crossinline func: PersonDetailView.() -> Unit) {
    dslAddView(func)
}

class PersonDetailView(context: Context) : BasePeopleView(context) {

    override fun view() {
        val state = StarWarsApplication.redukt.state
        val person = person ?: return

        val height = person.height
        val mass = person.mass
        val hair_color = person.hair_color
        val skin_color = person.skin_color
        val eye_color = person.eye_color
        val birth_year = person.birth_year
        val gender = person.gender
        val homeWorld = state.getHomeWorld(state, person)
        val species = state.getSpecies(state, person)

        val content = mapOf(
            R.string.height to "$height cm",
            R.string.mass to "$mass kg",
            R.string.hair_color to hair_color,
            R.string.skin_color to skin_color,
            R.string.eye_color to eye_color,
            R.string.birth_year to birth_year,
            R.string.gender to gender,
            R.string.home_world to homeWorld,
            R.string.specie to species
        )

        cardLayout {
            person(person)
            content(content)
        }
    }
}