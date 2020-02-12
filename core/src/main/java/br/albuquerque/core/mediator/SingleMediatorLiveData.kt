package br.albuquerque.core.mediator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

class SingleMediatorLiveData<T> : MediatorLiveData<T>() {

    var lastLiveData: LiveData<*>? = null

    override fun <S : Any?> addSource(source: LiveData<S>, onChanged: Observer<in S>) {
        lastLiveData?.let { removeSource(it) }
        lastLiveData = source
        super.addSource(source, onChanged)
    }

    fun emit(source: LiveData<T>) {
        addSource(source) { value = it }
    }
}