package br.com.example.starwars.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.example.starwars.data.entities.ApiPeople

@Dao
interface PeopleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(apiPeople: List<ApiPeople>)

    @Query("SELECT * FROM people")
    fun getListPeople(): PagingSource<Int, ApiPeople>

    @Query("UPDATE people SET favorite = :favorite WHERE id = :id")
    suspend fun updatePerson(favorite: Boolean, id: Int)
}