package com.popcode.starwars.data.local;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.popcode.starwars.data.local.dao.CharacterDao;
import com.popcode.starwars.data.local.entity.CharacterElement;

import java.util.List;

public class CharacterRepository {

    private CharacterDao characterDao;

    public CharacterRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        characterDao = db.characterDao();
    }

    public LiveData<List<CharacterElement>> getAllCharacters(List<CharacterElement> elements) {
        if (elements != null)
            new insertAsyncTask(characterDao).execute(elements);


        return characterDao.getAllCharacters();
    }

    public LiveData<CharacterElement> getCharacter(String name) {

        return characterDao.getCharacter(name);
    }

    public LiveData<Boolean> favoriteCharacter(Integer characterId, Boolean loved) {
        CharacterElement characterFounded = characterDao.getCharacterById(characterId).getValue();
        if (characterFounded != null){
            characterFounded.loved = loved;
            characterDao.insert(characterFounded);
        }
        MutableLiveData<Boolean> newLoved = new MutableLiveData<>();
        newLoved.setValue(loved);
        return newLoved;
    }

    private static class insertAsyncTask extends AsyncTask<List<CharacterElement>, Void, LiveData<List<CharacterElement>>> {

        private CharacterDao mAsyncTaskDao;

        insertAsyncTask(CharacterDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<CharacterElement>> doInBackground(final List<CharacterElement>... params) {
            if (params[0] != null)
            {
                for (CharacterElement element : params[0]){
                    if (element.species != null){
                        if (element.species.size() > 0)
                            element.specie = element.species.get(0);
                    }
                    String[] splitted = element.url.split("/");
                    element.id = Integer.valueOf(splitted[splitted.length - 1]);
                    mAsyncTaskDao.insert(element);
                }

            }
            return mAsyncTaskDao.getAllCharacters();
        }
    }

}
