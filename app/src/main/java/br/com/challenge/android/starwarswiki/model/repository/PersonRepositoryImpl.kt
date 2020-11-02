package br.com.challenge.android.starwarswiki.model.repository

import android.content.Context
import br.com.challenge.android.starwarswiki.model.data.dto.ApiPerson
import br.com.challenge.android.starwarswiki.model.data.mapper.ListMapper
import br.com.challenge.android.starwarswiki.model.domain.Person
import br.com.challenge.android.starwarswiki.utils.CheckNetwork
import io.reactivex.Observable

class PersonRepositoryImpl(
    private val listMapper: ListMapper<ApiPerson, Person>,
    private val context: Context
): PersonRepository {
    private val checkNetwork = CheckNetwork()

    override fun getPersonByName(name: String): Observable<List<Person>> {
        checkNetwork.checkIfDeviceIsReadyToConnectInternet(context)

        if(!CheckNetwork.isNetworkConnected){
            // query data from DAO
        }

        TODO("Not yet implemented")
    }

    override fun getPeople(): Observable<List<Person>> {
        checkNetwork.checkIfDeviceIsReadyToConnectInternet(context)

        if(!CheckNetwork.isNetworkConnected){
            // query data from DAO
        }

        TODO("Not yet implemented")
    }

}