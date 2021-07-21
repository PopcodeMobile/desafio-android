package br.com.star_wars_wiki.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.star_wars_wiki.database.RoomDatabase;
import br.com.star_wars_wiki.database.dao.FavoriteDAO;
import br.com.star_wars_wiki.entity.Favorite;

public class FavoriteRepo {
    private RoomDatabase database;
    private LiveData<List<Favorite>> getAllFavorites;

    public FavoriteRepo(Application application){
        database = RoomDatabase.getInstance(application.getBaseContext());
        getAllFavorites = database.favoritesDAO().getAllFavorites();
    }

    public void insert(Favorite favorite){
        new InsertAsyncTask(database).execute(favorite);
    }

    public LiveData<List<Favorite>> getAllFavorites(){
        return this.getAllFavorites;
    }

    public void remove(Favorite favorite){
        new RemoveAsyncTask(database).execute(favorite);
    }

    static class InsertAsyncTask extends AsyncTask<Favorite, Void, Void>{
        private FavoriteDAO favoriteDAO;

        InsertAsyncTask(RoomDatabase roomDatabase){
            favoriteDAO = roomDatabase.favoritesDAO();
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDAO.insertFavorite(favorites[0]);
            return null;
        }
    }

    static class RemoveAsyncTask extends AsyncTask<Favorite, Void, Void>{
        private FavoriteDAO favoriteDAO;

        RemoveAsyncTask(RoomDatabase roomDatabase){
            favoriteDAO = roomDatabase.favoritesDAO();
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDAO.removeFavorite(favorites[0]);
            return null;
        }
    }
}
