package br.com.jaysonsabino.desafioandroidpopcode.util;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;

public class DiffPeopleCallback extends DiffUtil.ItemCallback<Character> {
    @Override
    public boolean areItemsTheSame(@NonNull Character character, @NonNull Character t1) {
        return character.getId() == t1.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Character character, @NonNull Character t1) {
        return character == t1;
    }
}
