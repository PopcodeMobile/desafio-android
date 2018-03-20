package com.example.lucvaladao.entrevistapopcode.mvp.offline;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.example.lucvaladao.entrevistapopcode.db.MyApp;
import com.example.lucvaladao.entrevistapopcode.entity.Character;

import java.util.List;

/**
 * Created by lucvaladao on 3/20/18.
 */

public class OfflineService extends IntentService implements OfflineInterface.OnConcludeListener {

    public static final String ACTION = ".mvp.OfflineService";
    private static List<Character> characterListAux;
    private OfflineInterface offlineInterface = new OfflineInterfaceImpl();

    public OfflineService() {
        super("OfflineService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null){
            final String action = intent.getAction();
            if (ACTION.equals(action)){
                offlineInterface.getAllCharacterFailure(this);
            }
        }
    }

    @Override
    public void onConclude(List<Character> characterList) {
        characterListAux = characterList;
    }
}
