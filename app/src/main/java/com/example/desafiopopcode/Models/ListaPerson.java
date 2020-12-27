package com.example.desafiopopcode.Models;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaPerson<P> implements Serializable {

    public int count;
    public String next;
    public String previous;
    public ArrayList<P> results;

    public boolean hasMore() {
        return !TextUtils.isEmpty(next);
    }

    @Override
    public String toString() {
        return "Lista de Personagens:\n" + results;
    }
}