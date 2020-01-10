package br.com.starwarswiki.middlewares

import android.content.Context
import br.com.starwarswiki.actions.ActionCreator
import br.com.starwarswiki.actions.Actions
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.services.SWServiceApi.getPeople
import br.com.starwarswiki.services.SWServiceApi.getSpecies
import com.github.raulccabreu.redukt.actions.Action
import com.github.raulccabreu.redukt.middlewares.BeforeAction

class SpeciesMiddleware(context: Context) : NetworkOnMiddleware(context) {

    @BeforeAction(Actions.SYNC_SPECIES)
    fun synSpecies(state: AppState, action: Action<*>) {
        getSpecies { species, _ ->
            if (species != null) ActionCreator.saveResponseSpecie(species)
        }
    }
}