package br.com.jaysonsabino.desafioandroidpopcode.util;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import br.com.jaysonsabino.desafioandroidpopcode.entities.Character;

public class DiffPeopleCallback extends DiffUtil.ItemCallback<Character.CharacterWithFavorite> {
    @Override
    public boolean areItemsTheSame(@NonNull Character.CharacterWithFavorite characterWithFavorite, @NonNull Character.CharacterWithFavorite t1) {
        return characterWithFavorite.getCharacter().getId() == t1.getCharacter().getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Character.CharacterWithFavorite characterWithFavorite, @NonNull Character.CharacterWithFavorite t1) {
        return characterWithFavorite == t1;
    }
}
