package com.example.administrador.starwarswiki;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Favorite {
    /*Why not add a favorite id in the character class?
    It's easier to deal with data this way
     */
    @PrimaryKey
    private int starWarsCharacterId;

    public int getStarWarsCharacterId() {
        return starWarsCharacterId;
    }

    public void setStarWarsCharacterId(int starWarsCharacterId) {
        this.starWarsCharacterId = starWarsCharacterId;
    }

    public Favorite(int starWarsCharacterId) {
        this.starWarsCharacterId = starWarsCharacterId;
    }
}
