package com.albuquerque.starwarswiki.app.view.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.albuquerque.starwarswiki.R

@BindingAdapter("android:src")
fun setSrcDrawable(view: ImageView, isFavorite: Boolean) {

    if(isFavorite)
        view.setImageResource(R.drawable.ic_star_on)
    else
        view.setImageResource(R.drawable.ic_star_off)
}