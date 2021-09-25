package com.arthurgonzaga.wikistarwars.api.responses

import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PageResponseTest {

    @Test
    fun `should return null when next page is null`() {
        // Given
        val pageResponse = PageResponse(
            count = 87,
            next = null,
            previous = "https://swapi.dev/api/people/?page=8",
            result = listOf()
        )

        // When
        val nextPageIndex = pageResponse.getNextPageIndex()

        // Then
        assertThat(nextPageIndex).isEqualTo(null)
    }

    @Test
    fun `should return next page index when it has a next Page`() {
        // Given
        val pageResponse = PageResponse(
            count = 87,
            next = "https://swapi.dev/api/people/?page=9",
            previous = "https://swapi.dev/api/people/?page=7",
            result = listOf()
        )

        // When
        val nextPageIndex = pageResponse.getNextPageIndex()

        // Then
        assertThat(nextPageIndex).isEqualTo(9)
    }

    @Test
    fun `should return null when previous page is null`() {
        // Given
        val pageResponse = PageResponse(
            count = 87,
            next = "https://swapi.dev/api/people/?page=2",
            previous = null,
            result = listOf()
        )

        // When
        val previousPageIndex = pageResponse.getPreviousIndex()

        // Then
        assertThat(previousPageIndex).isEqualTo(null)
    }

    @Test
    fun `should return previous page index when it has a previous Page`() {
        // Given
        val pageResponse = PageResponse(
            count = 87,
            next = "https://swapi.dev/api/people/?page=3",
            previous = "https://swapi.dev/api/people/?page=1",
            result = listOf()
        )

        // When
        val previousPageIndex = pageResponse.getPreviousIndex()

        // Then
        assertThat(previousPageIndex).isEqualTo(1)
    }
}