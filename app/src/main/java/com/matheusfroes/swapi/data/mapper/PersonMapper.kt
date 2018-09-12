package com.matheusfroes.swapi.data.mapper

import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.extractIdFromUrl
import com.matheusfroes.swapi.network.data.PersonResponse

class PersonMapper {
    companion object {
        fun map(personResponse: PersonResponse): Person {
            val person = Person(
                    id = extractIdFromUrl(personResponse.url),
                    name = personResponse.name,
                    height = personResponse.height,
                    mass = personResponse.mass,
                    hairColor = personResponse.hairColor,
                    skinColor = personResponse.skinColor,
                    eyeColor = personResponse.eyeColor,
                    birthYear = personResponse.birthYear,
                    homeworld = personResponse.homeworld,
                    species = personResponse.species.joinToString(),
                    gender = personResponse.gender,
                    isBookmarked = false
            )
            return person
        }

        fun map(peopleResponse: List<PersonResponse>): List<Person> {
            return peopleResponse.map { map(it) }
        }
    }
}