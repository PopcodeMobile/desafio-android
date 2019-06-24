package com.example.entrevistapopcode.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.entrevistapopcode.api.entity.entity.Person;
import com.example.entrevistapopcode.db.dao.PersonDAO;


@Database(entities = {Person.class}, version = 3 ,exportSchema = false)
public abstract class StarWarsRoomDatabase extends RoomDatabase {

    public abstract PersonDAO wordDao();

    private static volatile StarWarsRoomDatabase INSTANCE;

    public static StarWarsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StarWarsRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            StarWarsRoomDatabase.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}