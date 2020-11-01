package br.com.challenge.android.starwarswiki.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PersonDatabaseDao {

    @Insert
    fun insert(person: PersonEntity)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param person new value to write
     */
    @Update
    fun update(person: PersonEntity)

    @Query("SELECT * from person WHERE name = :keyName")
    fun get(keyName: String): PersonEntity?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM person")
    fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * Sorted by name in descending order.
     */
    @Query("SELECT * FROM person ORDER BY name DESC")
    fun getAllNights(): LiveData<List<PersonEntity>>

}