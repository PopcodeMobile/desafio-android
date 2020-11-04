package br.com.challenge.android.starwarswiki.model.repository

import android.content.Context
import br.com.challenge.android.starwarswiki.model.api.ApiRetrofitService
import br.com.challenge.android.starwarswiki.model.api.SwapiService
import br.com.challenge.android.starwarswiki.model.data.dto.ApiPerson
import br.com.challenge.android.starwarswiki.model.data.mapper.ListMapper
import br.com.challenge.android.starwarswiki.model.database.PersonDatabase
import br.com.challenge.android.starwarswiki.model.database.PersonDatabaseDao
import br.com.challenge.android.starwarswiki.model.database.PersonEntity
import br.com.challenge.android.starwarswiki.model.domain.Person
import br.com.challenge.android.starwarswiki.utils.CheckNetwork
import io.reactivex.Observable

class PersonRepositoryImpl(
    private val listApiMapper: ListMapper<ApiPerson, Person>,
    private val listDaoMapper: ListMapper<PersonEntity, Person>,
    context: Context
): PersonRepository {
    private var personDatabaseDao: PersonDatabaseDao

    init {
        CheckNetwork().checkIfDeviceIsReadyToConnectInternet(context)
        personDatabaseDao = PersonDatabase.getInstance(context).personDatabaseDao
    }

    override fun getPersonByName(name: String): Observable<List<Person>> {
        if(!CheckNetwork.isNetworkConnected){
            val personEntity = personDatabaseDao.get(name)

            return personEntity.map {
                listDaoMapper.map(it)
            }
        }

        val personByName = getRetrofitService().getPersonByName(name)

        return personByName.map{
            listApiMapper.map(it.results)
        }

    }

    override fun getPeople(): Observable<List<Person>> {
        if(!CheckNetwork.isNetworkConnected){
            val people = personDatabaseDao.getPeople()

            return people.map {
                listDaoMapper.map(it)
            }
        }

        val allPeople = getRetrofitService().getAllPeople()

        return allPeople.map{
            listApiMapper.map(it.results)
        }

    }

    private fun getRetrofitService(): SwapiService {
        return ApiRetrofitService.getInstance().create(SwapiService::class.java)
    }

}