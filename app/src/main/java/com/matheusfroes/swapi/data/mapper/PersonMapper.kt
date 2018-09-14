package com.matheusfroes.swapi.data.mapper

import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.extra.extractIdFromUrl
import com.matheusfroes.swapi.network.data.PersonResponse

/**
 * Converts a [PersonResponse] to [Person]
 * Used to separate responsabilities between response objects and data models
 */
class PersonMapper {
    companion object {
        private fun map(personResponse: PersonResponse): Person {
            return Person(
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
        }

        fun map(peopleResponse: List<PersonResponse>): List<Person> {
            return peopleResponse.map { map(it) }
        }
    }
}