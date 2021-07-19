package br.com.star_wars_wiki.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.com.star_wars_wiki.entity.Specie;

@Dao
public interface SpeciesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Specie specie);

    @Query("SELECT * FROM Specie")
    LiveData<List<Specie>> getAllSpecies();
}
