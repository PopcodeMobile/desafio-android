package br.com.jaysonsabino.desafioandroidpopcode.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class FavoriteCharacter {

    @PrimaryKey
    private Integer characterId;
    private boolean syncedWithApi;

    public FavoriteCharacter() {
    }

    public FavoriteCharacter(Integer characterId) {
        this.characterId = characterId;
        this.syncedWithApi = false;
    }

    public Integer getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Integer characterId) {
        this.characterId = characterId;
    }

    public boolean isSyncedWithApi() {
        return syncedWithApi;
    }

    public void setSyncedWithApi(boolean syncedWithApi) {
        this.syncedWithApi = syncedWithApi;
    }
}
