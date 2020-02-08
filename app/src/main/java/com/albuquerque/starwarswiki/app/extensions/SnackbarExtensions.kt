package com.albuquerque.starwarswiki.app.extensions

import android.graphics.Color
import com.google.android.material.snackbar.Snackbar

fun Snackbar.success(){
    this.view.setBackgroundColor(Color.parseColor("#009933"))
    return this.show()
}

fun Snackbar.error(){
    this.view.setBackgroundColor(Color.parseColor("#e60000"))
    return this.show()
}