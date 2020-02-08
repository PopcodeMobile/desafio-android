package com.albuquerque.starwarswiki.app.extensions

import android.view.View

fun View.setGone() {
    this.visibility = View.GONE
}

fun View.setInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.setVisible() {
    this.visibility = View.VISIBLE
}