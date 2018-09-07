package br.com.jaysonsabino.desafioandroidpopcode.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;

@Database(entities = {Character.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CharacterDAO getCharacterDAO();
}
