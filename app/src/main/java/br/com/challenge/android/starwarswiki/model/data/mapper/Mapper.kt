package br.com.challenge.android.starwarswiki.model.data.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}