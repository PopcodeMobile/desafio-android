package br.com.starwarswiki.activities

import android.content.Context
import android.content.Intent
import br.com.starwarswiki.StarWarsApplication
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.views.peopleFavoriteLayout

class PeopleFavoriteActivity : ReactiveActivity() {

    companion object {
        fun open(context: Context) {
            context.startActivity(Intent(context, PeopleFavoriteActivity::class.java))
        }
    }

    override fun content() {
        val state = StarWarsApplication.redukt.state
        val people = state.people.values.filter { it.isFavorite }

        peopleFavoriteLayout { people(people) }
    }

    override fun initialState() { }

    override fun hasChanged(newState: AppState, oldState: AppState) = false

    override fun onChanged(state: AppState) { }
}