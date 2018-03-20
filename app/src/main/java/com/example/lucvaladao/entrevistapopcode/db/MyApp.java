package com.example.lucvaladao.entrevistapopcode.db;

import android.app.Application;
import android.arch.persistence.room.Room;

/**
 * Created by lucvaladao on 3/19/18.
 */

public class MyApp extends Application {

    public static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, AppDatabase.class, "roomDB").build();
    }
}
