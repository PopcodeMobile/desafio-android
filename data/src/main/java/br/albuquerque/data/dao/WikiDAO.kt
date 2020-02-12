package br.albuquerque.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.albuquerque.data.entity.ConfigEntity
import br.albuquerque.data.entity.PersonEntity

@Dao
interface WikiDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(people: List<PersonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(config: ConfigEntity)

    @Query("select * from config where id = 1")
    fun getConfig(): LiveData<ConfigEntity?>

    @Query("select * from people")
    fun getPeople(): LiveData<List<PersonEntity>>

    @Query("select * from people where isFavorite = 1")
    fun getOnlyPeopleFavorited(): LiveData<List<PersonEntity>>

    @Query("select * from people where tryAgainPosition is not null")
    fun getOnlyPeopleTryAgain(): LiveData<MutableList<PersonEntity>>

    @Query("select * from people")
    suspend fun getPeopleSuspend(): List<PersonEntity>

    @Query("delete from people")
    fun deleteAll()

    @Update
    suspend fun updatePerson(person: PersonEntity)

    @Transaction
    suspend fun saveAllTransaction(people: List<PersonEntity>, shouldClearTable: Boolean) {

        if (shouldClearTable)
            deleteAll()

        saveAll(people)

    }

}