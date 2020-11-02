package br.com.challenge.android.starwarswiki.model.repository

import br.com.challenge.android.starwarswiki.model.domain.Person
import io.reactivex.Observable

interface PersonRepository {

    fun getPersonByName(name: String): Observable<List<Person>>

    fun getPeople(): Observable<List<Person>>

}