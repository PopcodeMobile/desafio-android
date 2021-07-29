package com.example.desafio_android.ui.main.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import com.example.desafio_android.data.repository.Repository

class ListFavoriteViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    val getFavorites = repository.getFavorites()

}