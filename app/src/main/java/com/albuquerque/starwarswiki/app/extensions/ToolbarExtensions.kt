package com.albuquerque.starwarswiki.app.extensions

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

fun AppCompatActivity.setupToolbar(
    toolbar: Toolbar,
    title: String,
    action: (ActionBar.() -> Unit)? = null
) {

    setSupportActionBar(toolbar)

    supportActionBar?.title = title

    action?.let { supportActionBar?.run { it() }  }
}