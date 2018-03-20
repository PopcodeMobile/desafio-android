package com.example.lucvaladao.entrevistapopcode.mvp.offline;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.lucvaladao.entrevistapopcode.db.MyApp;
import com.example.lucvaladao.entrevistapopcode.entity.Character;

import java.util.List;

/**
 * Created by lucvaladao on 3/20/18.
 */

class OfflineInterfaceImpl implements OfflineInterface {
    @SuppressLint("StaticFieldLeak")
    @Override
    public void getAllCharacterFailure(final OnConcludeListener listener) {
        new  AsyncTask<Void, Void, List<Character>>() {
            @Override
            protected List<Character> doInBackground(Void... params) {
                return MyApp.database.characterDAO().getAllCharacters();
            }

            @Override
            protected void onPostExecute(List<Character> characterList) {
                listener.onConclude(characterList);
            }
        }.execute();
    }
}
