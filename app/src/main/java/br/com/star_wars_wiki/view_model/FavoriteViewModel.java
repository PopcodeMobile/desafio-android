package br.com.star_wars_wiki.view_model;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.star_wars_wiki.database.repository.FavoriteRepo;
import br.com.star_wars_wiki.entity.Favorite;

public class FavoriteViewModel extends AndroidViewModel {
    private FavoriteRepo favoriteRepo;
    private LiveData<List<Favorite>> getAllFavorite;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        favoriteRepo = new FavoriteRepo(application);
        getAllFavorite = favoriteRepo.getAllFavorites();
    }

    public void insert(Favorite favorite){
        favoriteRepo.insert(favorite);
    }

    public void remove(Favorite favorite, Context context){
        favoriteRepo.remove(favorite, context);
    }

    public LiveData<List<Favorite>> getAllFavorites(){
        return this.getAllFavorite;
    }
}
