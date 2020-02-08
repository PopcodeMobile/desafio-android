package com.albuquerque.starwarswiki.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.albuquerque.starwarswiki.core.network.StringWrapper

abstract class WikiViewModel : ViewModel() {

    val onError = MutableLiveData<StringWrapper>()

}