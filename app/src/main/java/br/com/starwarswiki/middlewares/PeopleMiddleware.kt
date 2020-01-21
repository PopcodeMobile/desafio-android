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

    companion object {
        const val maxIdToRequest = 9
    }

    @BeforeAction(Actions.SYNC_PEOPLE)
    fun syncPeople(state: AppState, action: Action<*>) {
        val map: MutableMap<String, Person> = mutableMapOf()
        val nPageToRequest = 1

        requestPeople(nPageToRequest) { response ->
            if (response != null) map.putAll(response.results.map { it.name to it })
            ActionCreator.saveResponsePerson(map)
        }
    }

    private fun requestPeople(pageId: Int, onFinishRequest: (
        (people: ServerResponse<Person>?) -> Unit)) {

        println("logd started sync")
        ActionCreator.updateSyncStatus(true)

        getPeople(pageId) { responsePeople, _ ->
            onFinishRequest.invoke(responsePeople)
            if(pageId < maxIdToRequest) {
                requestPeople(pageId+1, onFinishRequest)
            }
        }

        ActionCreator.updateSyncStatus(false)
        println("logd finished sync")
    }
}