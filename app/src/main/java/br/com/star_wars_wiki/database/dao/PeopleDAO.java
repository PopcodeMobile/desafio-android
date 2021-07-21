package br.com.star_wars_wiki.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.com.star_wars_wiki.entity.People;

@Dao
public interface PeopleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllPeople(List<People> list);

    @Query("SELECT * FROM People")
    LiveData<List<People>> getAllPeople();
}
