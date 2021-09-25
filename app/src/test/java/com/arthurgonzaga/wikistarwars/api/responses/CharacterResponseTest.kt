package com.arthurgonzaga.wikistarwars.api.responses

import com.google.common.truth.Truth.assertThat
import com.google.gson.annotations.SerializedName
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CharacterResponseTest {

    @Test
    fun `shoud return null when the list is empty`() {

        // Given
        val characterResponse = CharacterResponse(speciesUrls = listOf())

        // When
        val specie =  characterResponse.getSpecieUrl()

        // Then
        assertThat(specie).isEqualTo(null)
    }

    @Test
    fun `should return the first index when the list is not empty`() {

        val url1 = "https://swapi.dev/api/species/32/"
        val url2 = "https://swapi.dev/api/species/33/"

        // Given
        val characterResponse = CharacterResponse(speciesUrls = listOf(url1, url2))

        // When
        val specie =  characterResponse.getSpecieUrl()

        // Then
        assertThat(specie).isEqualTo(url1)
    }
}