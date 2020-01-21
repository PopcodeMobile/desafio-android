package br.com.starwarswiki.activities

import android.content.Context
import android.content.Intent
import br.com.starwarswiki.StarWarsApplication
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.views.personDetailLayout

class PersonDetailActivity : ReactiveActivity() {

    companion object {
        fun open(context: Context, personName: String) {
            context.startActivity(Intent(context, PersonDetailActivity::class.java).apply {
                putExtra("person_name", personName)
            })
        }
    }

    override fun content() {
        val state = StarWarsApplication.redukt.state
        val name = intent.extras?.getString("person_name") ?: ""
        val person = state.people.values.firstOrNull { it.name == name } ?: return

        println("load person detail activity) content ran")
        personDetailLayout { person(person) }
    }

    override fun initialState() { }

    override fun hasChanged(newState: AppState, oldState: AppState) = false

    override fun onChanged(state: AppState) { }
}
