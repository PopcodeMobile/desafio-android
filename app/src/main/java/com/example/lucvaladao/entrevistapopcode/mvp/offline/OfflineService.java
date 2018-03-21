package com.example.lucvaladao.entrevistapopcode.mvp.offline;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.lucvaladao.entrevistapopcode.entity.Character;
import com.example.lucvaladao.entrevistapopcode.mvp.offline.OfflineInteractor.OnConcludeListener;
import com.example.lucvaladao.entrevistapopcode.mvp.offline.OfflineInteractor.PostCharacterRemoteListener;

import java.util.List;

/**
 * Created by lucvaladao on 3/20/18.
 */

public class OfflineService extends IntentService implements OnConcludeListener, PostCharacterRemoteListener {

    public static final String ACTION = ".mvp.offline.OfflineService";
    private OfflineInteractor offlineInteractor = new OfflineInteractorImpl();
    private static List<Character> characterListAux;

    public OfflineService() {
        super("OfflineService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION.equals(action)) {
                offlineInteractor.getAllCharacterFailure(this);
            }
        }
    }

    @Override
    public void onConclude(List<Character> characterList) {
        int index = 0;
        characterListAux = characterList;
        if (!characterList.isEmpty()) {
            for (Character character : characterList) {
                String auxHeader = Math.random() < 0.5 ? "400" : "201";
                offlineInteractor.postCharacterRemote(character, String.valueOf(index++), auxHeader, this);
            }
        }

    }


    @Override
    public void onPostCharacterRemoteSuccess(Character character) {
        character.setRequestStatus(true);
        offlineInteractor.putCHaracterIntoDB(character);
    }

    @Override
    public void onPostCharacterRemoteFailure(Character character) {
        character.setRequestStatus(false);
        offlineInteractor.putCHaracterIntoDB(character);
    }
}
