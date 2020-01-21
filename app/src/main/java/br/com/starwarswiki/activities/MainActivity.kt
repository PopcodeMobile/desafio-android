package br.com.starwarswiki.activities

import android.view.Menu
import android.view.MenuItem
import br.com.starwarswiki.R
import br.com.starwarswiki.actions.ActionCreator
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.views.peopleLayout

class MainActivity : ReactiveActivity() {
    override fun initialState() {
        syncContent()
    }

    override fun content() {
        peopleLayout {  }
    }

    override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
        return newState.syncRunning != oldState.syncRunning && !newState.syncRunning
    }

    override fun onChanged(state: AppState) {

    }

    private fun syncContent() {
        ActionCreator.syncPeople()
        ActionCreator.syncPlanets()
        ActionCreator.syncSpecies()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.action_Favorite)?.isVisible = true
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_Favorite -> PeopleFavoriteActivity.open(this)
        }
        return super.onOptionsItemSelected(item)
    }
}
