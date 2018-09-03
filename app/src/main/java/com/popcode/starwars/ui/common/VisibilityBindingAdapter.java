package com.popcode.starwars.ui.common;

import android.databinding.BindingAdapter;
import android.view.View;

public class VisibilityBindingAdapter {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
