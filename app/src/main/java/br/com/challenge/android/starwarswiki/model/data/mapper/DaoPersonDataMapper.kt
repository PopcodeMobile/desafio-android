package br.com.challenge.android.starwarswiki.model.data.mapper

import br.com.challenge.android.starwarswiki.model.database.PersonEntity
import br.com.challenge.android.starwarswiki.model.domain.Person

class DaoPersonDataMapper: Mapper<PersonEntity, Person> {

    override fun map(input: PersonEntity): Person {
        return Person(
            name = input.name,
            birthYear = input.birthYear,
            eyeColor = input.eyeColor,
            gender = input.gender,
            hairColor = input.hairColor,
            height = input.height,
            mass = input.mass,
            skinColor = input.skinColor,
            homeWorld = input.homeWorld/*,
            species = input.species*/
        )
    }

}