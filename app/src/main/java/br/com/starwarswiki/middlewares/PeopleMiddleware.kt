package br.com.starwarswiki.middlewares

import android.content.Context
import br.com.starwarswiki.actions.ActionCreator
import br.com.starwarswiki.actions.Actions
import br.com.starwarswiki.models.AppState
import br.com.starwarswiki.models.Person
import br.com.starwarswiki.models.ServerResponse
import br.com.starwarswiki.services.SWServiceApi.getPeople
import com.github.raulccabreu.redukt.actions.Action
import com.github.raulccabreu.redukt.middlewares.BeforeAction

class PeopleMiddleware(context: Context) : NetworkOnMiddleware(context) {


    @BeforeAction(Actions.SYNC_PEOPLE)
    fun syncPeople(state: AppState, action: Action<*>) {
        requestPeople(1)
    }

    private fun requestPeople(pageId: Int) {
        println("load started sync")
        ActionCreator.updateSyncStatus(true)

        getPeople(pageId) { response, _ ->
            savePeople(response)
            if (response?.next != null) {
                requestPeople(pageId + 1)
            }
        }

        ActionCreator.updateSyncStatus(false)
        println("load finished sync")
    }

    private fun savePeople(response: ServerResponse<Person>?) {
        response ?: return

        val map: MutableMap<String, Person> = mutableMapOf()
        map.putAll(response.results.map { it.name to it }.toMap())
        ActionCreator.saveResponsePerson(map)
    }


}