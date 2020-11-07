package br.com.challenge.android.starwarswiki.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val appContext: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PeopleViewModel::class.java)) {
            return PeopleViewModel(appContext) as T
        }

        throw IllegalArgumentException("Unknown class name!")
    }

}