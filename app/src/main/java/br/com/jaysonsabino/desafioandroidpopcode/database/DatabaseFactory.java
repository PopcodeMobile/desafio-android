package br.com.jaysonsabino.desafioandroidpopcode.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseFactory {

    private static final String DATABASE_NAME = "starwars";

    public AppDatabase getDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }
}
