package com.example.desafio_android.util

import java.lang.Exception

sealed class Resultado<out R> {
    data class Sucesso<out T>(val dado: T?) : Resultado<T?> ()
    data class Erro(val exception: Exception) : Resultado<Nothing>()
}