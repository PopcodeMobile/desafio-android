package br.com.starwarswiki.activities

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.views.PersonDetailsLayout

class PersonDetailActivity : ReactiveActivity() {

    companion object {
        fun open(context: Context, personName: String) {
            context.startActivity(Intent(context, PersonDetailActivity::class.java).apply {
                putExtra("person_name", personName)
            })
        }
    }

    override fun render(): View {
        val name = intent.extras?.getString("person_name") ?: ""
        return PersonDetailsLayout(this, name)
    }

    override fun initialState() { }

    override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
        return newState != oldState
    }

    override fun onChanged(state: AppState) {
        Log.d("testFavorite", "PersonDetailActivity onChanged()")
        this@PersonDetailActivity.render()
    }
}
