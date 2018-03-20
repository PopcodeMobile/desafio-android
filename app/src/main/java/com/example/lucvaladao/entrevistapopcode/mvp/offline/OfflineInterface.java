package com.example.lucvaladao.entrevistapopcode.mvp.offline;

import com.example.lucvaladao.entrevistapopcode.entity.Character;

import java.util.List;

/**
 * Created by lucvaladao on 3/20/18.
 */

public interface OfflineInterface {
    interface OnConcludeListener {
        void onConclude(List<Character> characterList);
    }

    void getAllCharacterFailure(OnConcludeListener listener);
}
