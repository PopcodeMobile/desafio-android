package br.com.jaysonsabino.desafioandroidpopcode.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;
import br.com.jaysonsabino.desafioandroidpopcode.entities.FavoriteCharacter;

@Database(entities = {Character.class, FavoriteCharacter.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CharacterDAO getCharacterDAO();

    public abstract FavoriteCharacterDAO getFavoriteCharacterDAO();
}
