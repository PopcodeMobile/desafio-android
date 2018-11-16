package com.example.administrador.starwarswiki;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {StarWarsCharacter.class}, version = 1)
public abstract class StarWarsDatabase extends RoomDatabase {
    public abstract StarWarsCharacterDao starWarsCharacterDao();

    private static volatile StarWarsDatabase INSTANCE;

    static StarWarsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StarWarsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            StarWarsDatabase.class, "star_wars_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


