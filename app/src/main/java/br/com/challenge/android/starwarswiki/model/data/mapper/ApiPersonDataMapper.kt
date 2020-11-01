package br.com.challenge.android.starwarswiki.model.data.mapper

import br.com.challenge.android.starwarswiki.model.data.dto.ApiPerson
import br.com.challenge.android.starwarswiki.model.domain.Person

class ApiPersonDataMapper: Mapper<ApiPerson, Person> {

    override fun map(input: ApiPerson): Person {
        return Person(
                name = input.name,
                birthYear = input.birthYear,
                eyeColor = input.eyeColor,
                gender = input.gender,
                hairColor = input.hairColor,
                height = input.height,
                mass = input.mass,
                skinColor = input.skinColor,
                homeWorld = input.homeWorld,
                species = input.species
        )
    }

}