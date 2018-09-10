package com.popcode.starwars.util;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;

public class FloatingActionButtonFavoriteState {

    public static void setViewState(FloatingActionButton floatingActionButton, int drawableId, int colorId) {
        floatingActionButton.setImageDrawable(floatingActionButton.getResources().getDrawable(drawableId));
        floatingActionButton.setBackgroundTintList(ContextCompat.getColorStateList(floatingActionButton.getContext(), colorId));
    }
}
