package br.com.star_wars_wiki.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.TypeConverters;

import br.com.star_wars_wiki.database.converters.Converters;
import br.com.star_wars_wiki.entity.People;
import br.com.star_wars_wiki.database.dao.*;
import br.com.star_wars_wiki.entity.Planet;
import br.com.star_wars_wiki.entity.Specie;

@Database(entities = {People.class, Planet.class, Specie.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class RoomDatabase extends androidx.room.RoomDatabase {
    private static final String DATABASE_NAME="CVDatabase";
    private static RoomDatabase INSTANCE;

    public abstract PeopleDAO peopleDao();
    public abstract PlanetDAO planetDAO();
    public abstract SpeciesDAO speciesDAO();

    public static RoomDatabase getInstance(Context context){
        INSTANCE =  Room.databaseBuilder(context, RoomDatabase.class, DATABASE_NAME).build();
        return INSTANCE;
    }
}
