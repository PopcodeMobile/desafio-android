package com.popcode.starwars.ui.common;

import android.databinding.BindingAdapter;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.popcode.starwars.R;
import com.popcode.starwars.util.FloatingActionButtonFavoriteState;

public class FavoriteFloatingActionButtonBindingAdapter {
    @BindingAdapter("loved")
    public static void showLoved(View view, boolean loved) {
        if (loved)
            FloatingActionButtonFavoriteState.setViewState((FloatingActionButton) view, R.drawable.ic_favorite_filled, R.color.colorPrimary);
        else
            FloatingActionButtonFavoriteState.setViewState((FloatingActionButton) view, R.drawable.ic_favorite, R.color.colorDetailListItem);

    }
}
