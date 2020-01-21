package br.com.starwarswiki.actions

object Actions {
    const val LOAD_DATABASE = "LOAD_DATABASE"
    const val LOADED_DATABASE = "LOADED_DATABASE"

    const val SYNC_STARTED = "SYNC_STARTED"

    const val SYNC_PEOPLE = "SYNC_PEOPLE"
    const val SAVE_PEOPLE = "SAVE_PEOPLE"

    const val SYNC_PLANETS = "SYNC_PLANETS"
    const val SAVE_PLANETS = "SAVE_PLANETS"

    const val SYNC_SPECIES = "SYNC_SPECIES"
    const val SAVE_SPECIES = "SAVE_SPECIES"

    const val ADD_FAVORITE = "ADD_FAVORITE"
    const val REMOVE_FAVORITE = "REMOVE_FAVORITE"

    const val FILTER_BY_FAVORITE = "FILTER_BY_FAVORITE"
    const val SEARCH_BY_NAME = "SEARCH_BY_NAME"
    const val RESULT = "FILTER_RESULT"

}