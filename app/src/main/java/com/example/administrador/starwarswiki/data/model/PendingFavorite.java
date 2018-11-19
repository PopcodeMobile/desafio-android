package com.example.administrador.starwarswiki.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class PendingFavorite {
    @PrimaryKey
    int id;

    public PendingFavorite(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
