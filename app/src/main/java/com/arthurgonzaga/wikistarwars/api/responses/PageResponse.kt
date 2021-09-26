package com.arthurgonzaga.wikistarwars.api.responses

data class PageResponse(
    val count: Int,
    private val next: String?,
    private val previous: String?,
    val results: List<CharacterResponse> = listOf()
){

    fun getNextPageIndex(): Int? {
        return next?.split("page=")?.get(1)?.toIntOrNull()
    }

    fun getPreviousIndex(): Int? {
        return previous?.split("page=")?.get(1)?.toIntOrNull()
    }

    override fun toString(): String {
        return "PageResponse(count=$count, next=${getNextPageIndex()}, previous=${getPreviousIndex()}, result=$results)"
    }
}
