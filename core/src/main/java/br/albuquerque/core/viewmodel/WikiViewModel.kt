package br.albuquerque.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.albuquerque.core.network.StringWrapper

abstract class WikiViewModel : ViewModel() {

    companion object {
        const val PAGINATION_SIZE = 10
        const val PAGINATION_FIRST_PAGE = 1
    }

    val onError = MutableLiveData<StringWrapper>()

}