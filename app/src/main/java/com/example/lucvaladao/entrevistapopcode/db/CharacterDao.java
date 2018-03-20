package com.example.lucvaladao.entrevistapopcode.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverter;

import com.example.lucvaladao.entrevistapopcode.entity.Character;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by lucvaladao on 3/19/18.
 */
@Dao
public interface CharacterDao {

    @Query("SELECT * FROM character")
    List<Character> getAllCharacters();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<Character> characterList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacter(Character character);

     class Converters {
        @TypeConverter
        public static List<String> fromString(String value) {
            Type listType = new TypeToken<List<String>>() {}.getType();
            return new Gson().fromJson(value, listType);
        }

        @TypeConverter
        public static String fromArrayLisr(List<String> list) {
            Gson gson = new Gson();
            String json = gson.toJson(list);
            return json;
        }
    }
}
