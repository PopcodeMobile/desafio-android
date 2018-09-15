package com.matheusfroes.swapi.data.dto

/** Represents a bookmark response event.
 * If the request was successfully sent, 'bookmarked' will be true.
 * 'message' carries the response message/error from the API
*/
data class BookmarkedEvent(
        val bookmarked: Boolean,
        val message: String
)