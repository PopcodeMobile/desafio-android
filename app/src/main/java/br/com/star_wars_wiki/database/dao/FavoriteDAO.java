package br.com.star_wars_wiki.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.com.star_wars_wiki.entity.Favorite;

@Dao
public interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(Favorite favorite);

    @Delete
    void removeFavorite(Favorite favorite);

    @Query("SELECT * FROM Favorite")
    LiveData<List<Favorite>> getAllFavorites();
}
