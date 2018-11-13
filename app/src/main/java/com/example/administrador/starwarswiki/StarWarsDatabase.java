package com.example.administrador.starwarswiki;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {StarWarsCharacter.class}, version = 1)
public abstract class StarWarsDatabase extends RoomDatabase {
    public abstract StarWarsCharacterDao starWarsCharacterDao();
}

