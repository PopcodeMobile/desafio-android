package com.popcode.starwars.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.popcode.starwars.data.local.dao.CharacterDao;
import com.popcode.starwars.data.local.entity.CharacterElement;

@Database(entities = {CharacterElement.class}, version = 10, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CharacterDao characterDao();

    private static AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "starwars_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
