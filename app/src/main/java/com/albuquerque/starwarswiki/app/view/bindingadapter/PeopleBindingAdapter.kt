package com.albuquerque.starwarswiki.app.view.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.albuquerque.starwarswiki.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("android:src")
fun setSrcDrawable(view: ImageView, isFavorite: Boolean) {

    if(isFavorite)
        view.setImageResource(R.drawable.ic_star_on)
    else
        view.setImageResource(R.drawable.ic_star_off)
}

@BindingAdapter("app:srcCompat")
fun setSrcDrawable(view: FloatingActionButton, isFavorite: Boolean) {

    if(isFavorite)
        view.setImageResource(R.drawable.ic_star_white_on)
    else
        view.setImageResource(R.drawable.ic_star_white_off)
}