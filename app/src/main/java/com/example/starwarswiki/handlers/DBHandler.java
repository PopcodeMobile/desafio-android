package com.example.starwarswiki.handlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    public DBHandler(Context context) {
        super(context, "starwarswiki", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE person (" +
                        "id INTEGER PRIMARY KEY," +
                        "name TEXT," +
                        "height TEXT," +
                        "mass TEXT," +
                        "hair_color TEXT," +
                        "skin_color TEXT," +
                        "eye_color TEXT," +
                        "birth_year TEXT," +
                        "gender TEXT," +
                        "homeworld TEXT," +
                        "species TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS person");
        onCreate(db);
    }
}
