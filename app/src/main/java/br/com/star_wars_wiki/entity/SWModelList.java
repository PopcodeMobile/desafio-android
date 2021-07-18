package br.com.star_wars_wiki.entity;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class SWModelList<T> implements Serializable {
    public int count;
    public String next;
    public String previous;
    public ArrayList<T> results;

    public boolean hasMore() {
        return !TextUtils.isEmpty(next);
    }
}