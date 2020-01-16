package br.com.starwarswiki.views.person

import android.content.Context
import br.com.starwarswiki.R
import br.com.starwarswiki.StarWarsApplication
import br.com.starwarswiki.models.Specie
import br.com.starwarswiki.views.CardLayout.cardLayout
import br.com.starwarswiki.views.dslAddView

inline fun personDetailView(crossinline func: PersonDetailView.() -> Unit) {
    dslAddView(func)
}

class PersonDetailView(context: Context) : BasePeopleView(context) {
    val state = StarWarsApplication.redukt.state

    override fun view() {
        val person = person ?: return

        val name = person.name
        val height = person.height
        val mass = person.mass
        val hair_color = person.hair_color
        val skin_color = person.skin_color
        val eye_color = person.eye_color
        val birth_year = person.birth_year
        val gender = person.gender
        val homeWorld = getHomeWorld()
        val species = getSpecies()
        val isfavorite = person.isFavorite

        cardLayout(context, name, isfavorite, mapOf(
            R.string.height to "$height cm",
            R.string.mass to "$mass kg",
            R.string.hair_color to hair_color,
            R.string.skin_color to skin_color,
            R.string.eye_color to eye_color,
            R.string.birth_year to birth_year,
            R.string.gender to gender,
            R.string.home_world to homeWorld,
            R.string.specie to species
        ))
    }

    private fun getHomeWorld(): String {
        val homeWorld = state.planets?.firstOrNull { it.url == person?.homeworld }
        return homeWorld?.name ?: ""
    }

    private fun getSpecies(): String {
        val speciesList: MutableList<Specie?> = mutableListOf()

        person?.species?.forEach { item ->
            speciesList.add(state.species?.firstOrNull {it.url == item})
        }

        val nameList: MutableList<String?> = mutableListOf()

        speciesList.forEach { specie ->
            nameList.add(specie?.name ?: "")
        }

        return nameList.toString()
    }

}