package br.com.starwarswiki.database

import br.com.starwarswiki.models.Person
import br.com.starwarswiki.models.Person_

object Queries {
    fun searchPeopleByName(name: String): List<Person> {
        return ObjectBox.boxStore?.boxFor(Person::class.java)?.query()
            ?.contains(Person_.name, name)
            ?.build()
            ?.find() ?: listOf()
    }

    fun filterPepleByFavorite(): List<Person> {
        return ObjectBox.boxStore?.boxFor(Person::class.java)?.query()
            ?.equal(Person_.isFavorite, true)
            ?.build()
            ?.find() ?: listOf()
    }
}
