package com.example.starwars.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: ResultEntity)

    @Query("SELECT * FROM result_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<ResultEntity>>  // Captura todos os dados da Entidade que esta no db
}