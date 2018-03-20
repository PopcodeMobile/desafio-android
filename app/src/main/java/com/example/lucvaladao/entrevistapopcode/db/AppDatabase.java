package com.example.lucvaladao.entrevistapopcode.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.lucvaladao.entrevistapopcode.entity.Character;

/**
 * Created by lucvaladao on 3/19/18.
 */

@Database(entities = {Character.class}, version = 1, exportSchema = false)
@TypeConverters({CharacterDao.Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CharacterDao characterDAO();
}
