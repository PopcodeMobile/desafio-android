package com.arthurgonzaga.wikistarwars.util

import android.widget.ImageButton
import androidx.core.content.res.ResourcesCompat
import com.arthurgonzaga.wikistarwars.R

fun ImageButton.setImage(isFavorite: Boolean) {
    val drawable = ResourcesCompat.getDrawable(
        context.resources,
        if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border,
        context.theme
    )
    this.setImageDrawable(drawable)
}