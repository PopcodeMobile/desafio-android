package com.example.starwars.repository

import androidx.lifecycle.LiveData
import com.example.starwars.data.room.ResultDao
import com.example.starwars.data.room.ResultEntity

class RepositoryRoom (private val resultDao: ResultDao)  {

    // Captura todos os dados da Entidade que esta no db
    val readAllData: LiveData<List<ResultEntity>> = resultDao.readAllData()

    // Adicionar Resultado
    suspend fun addResult(resultEntity: ResultEntity){
        resultDao.addResult(resultEntity)
    }

    // Deleta todos Results
    suspend fun deleteAllResults(){
        resultDao.deleteAllResults()
    }

}