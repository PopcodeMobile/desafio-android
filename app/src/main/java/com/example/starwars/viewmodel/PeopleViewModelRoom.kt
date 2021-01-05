package com.example.starwars.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.starwars.data.room.ResultDatabase
import com.example.starwars.data.room.ResultEntity
import com.example.starwars.repository.RepositoryRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PeopleViewModelRoom(application: Application) : AndroidViewModel(application) {

    // Captura todos os dados da Entidade que esta no db
    val readAllData: LiveData<List<ResultEntity>> // LiveData
    private val repositoryRoom: RepositoryRoom // Repositório

    // Inicializa os valores
    init {
        // Captura o Dao do Database
        val resultDao = ResultDatabase.getDatabase(application.baseContext).resultDao()
        repositoryRoom = RepositoryRoom(resultDao) // Repositorio recebe o Dao
        readAllData = repositoryRoom.readAllData // Captura todos os dados da tabela
    }

    // Adiciona Result
    fun addResult(resultEntity: ResultEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryRoom.addResult(resultEntity)
        }
    }

    // Deleta todos Results
    fun deleteAllResults() {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryRoom.deleteAllResults()
        }
    }

}