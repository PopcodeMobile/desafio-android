package br.albuquerque.core.network

sealed class WikiResult<T> {
    class Success<T>(val data: T): WikiResult<T>()
    class Failure<T>(val error: WikiException): WikiResult<T>()
}