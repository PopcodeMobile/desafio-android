package com.example.starwarswiki.handlers;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.starwarswiki.structural.Person;

@Database(entities = {Person.class}, version = 1,  exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder( context.getApplicationContext(), AppDatabase.class, "app-database").build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract PeopleDAO peopleDAO();

}
