package br.com.starwarswiki.middlewares

import android.content.Context
import br.com.starwarswiki.actions.ActionCreator
import br.com.starwarswiki.actions.Actions
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.services.SWServiceApi.getPeople
import com.github.raulccabreu.redukt.actions.Action
import com.github.raulccabreu.redukt.middlewares.BeforeAction

class PeopleMiddleware(context: Context) : NetworkOnMiddleware(context) {

    @BeforeAction(Actions.SYNC_PEOPLE)
    fun synPeople(state: AppState, action: Action<*>) {
        getPeople { people, _ ->
            if (people != null) ActionCreator.saveResponsePerson(people)
        }
    }
}