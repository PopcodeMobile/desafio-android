package com.example.lucvaladao.entrevistapopcode.mvp.favorite;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.lucvaladao.entrevistapopcode.db.MyApp;
import com.example.lucvaladao.entrevistapopcode.entity.Character;

import java.util.List;

/**
 * Created by lucvaladao on 3/19/18.
 */

class FavoriteInteractorImpl implements FavoriteInteractor {

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getCharacterFromDB(final GetCharacterFromDBListener listener) {
        new  AsyncTask<Void, Void, List<Character>>() {
            @Override
            protected List<Character> doInBackground(Void... params) {
                return MyApp.database.characterDAO().getAllCharacters();
            }

            @Override
            protected void onPostExecute(List<Character> characterList) {
                listener.onGetCharacterFromDBSuccess(characterList);
            }
        }.execute();
    }
}
