package com.matheusfroes.swapi.extra

sealed class Result<D> {

    class InProgress<D>(val cachedData: D? = null) : Result<D>()

    data class Complete<D>(val data: D) : Result<D>()

    data class Error<D>(val error: Throwable? = null) : Result<D>()

}