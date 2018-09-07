package br.com.jaysonsabino.desafioandroidpopcode.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class FavoriteCharacter {

    @PrimaryKey
    private int characterId;
    private boolean syncedWithApi;

    public FavoriteCharacter() {
    }

    public FavoriteCharacter(int characterId) {
        this.characterId = characterId;
        this.syncedWithApi = false;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public boolean isSyncedWithApi() {
        return syncedWithApi;
    }

    public void setSyncedWithApi(boolean syncedWithApi) {
        this.syncedWithApi = syncedWithApi;
    }
}
