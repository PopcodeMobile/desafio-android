package com.example.starwarswiki.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.lifecycle.LiveData;
import androidx.room.Update;

import com.example.starwarswiki.structural.Person;

import java.util.List;

@Dao
public interface PeopleDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertPerson(Person person);

    @Query("DELETE FROM person_table")
    void removeAllPerson();

    @Query("SELECT * FROM person_table")
    public LiveData<List<Person>> getAllPerson();

    @Query("SELECT * FROM person_table WHERE name LIKE :searchString")
    public LiveData<List<Person>> searchByName(String searchString);

    @Query("UPDATE person_table SET favorite = :favValue WHERE name like :stringName")
    public void updateFavorite(String stringName, int favValue);

}
