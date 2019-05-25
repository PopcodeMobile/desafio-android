package com.example.starwarswiki.handlers;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.lifecycle.LiveData;

import com.example.starwarswiki.structural.Person;

import java.util.List;

@Dao
public interface PeopleDAO {

    @Insert
    public void insertPerson(Person person);

    @Query("DELETE FROM person_table")
    void removeAllPerson();

    @Query("SELECT * FROM person_table")
    public LiveData<List<Person>> getAllPerson();

    @Query("SELECT * FROM person_table WHERE name LIKE :searchString")
    public LiveData<List<Person>> searchByName(String searchString);


}
