package com.example.entrevistapopcode.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.entrevistapopcode.api.entity.entity.Person;

import java.util.List;

@Dao
public interface PersonDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPerson(Person character);

    @Query("SELECT * from personagem_table")
    LiveData<List<Person>> getAllPerson();

    @Query("SELECT * from personagem_table WHERE name=:Personname")
    LiveData<Person> getPerson(String Personname);

    @Query("SELECT * from personagem_table WHERE name LIKE :Personname")
    LiveData<List<Person>> getAllPersonById(String Personname);

    @Query("SELECT loved from personagem_table WHERE name LIKE :Personname")
    LiveData<Boolean> isfavorite(String Personname);

    @Query("UPDATE personagem_table SET loved = :value WHERE name = :Personname")
    void favorite(String Personname, boolean value);

    @Query("SELECT * FROM personagem_table WHERE loved = :loved")
    LiveData<List<Person>> getAllFavorite(boolean loved);
}
