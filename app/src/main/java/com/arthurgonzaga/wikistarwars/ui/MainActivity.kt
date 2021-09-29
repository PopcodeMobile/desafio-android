package com.arthurgonzaga.wikistarwars.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.IdRes
import com.arthurgonzaga.wikistarwars.R
import com.arthurgonzaga.wikistarwars.ui.components.MyThemes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getMyTheme())
        setContentView(R.layout.activity_main)

    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

    private fun getMyTheme(): Int{
        val sharedPref = this.getSharedPreferences(getString(R.string.theme_key), Context.MODE_PRIVATE)

        return when(sharedPref.getInt("theme", MyThemes.YELLOW.ordinal)){
            MyThemes.YELLOW.ordinal -> R.style.Theme_WikiStarWars_Yellow
            MyThemes.BLUE.ordinal -> R.style.Theme_WikiStarWars_Blue
            MyThemes.RED.ordinal -> R.style.Theme_WikiStarWars_Red
            else -> R.style.Theme_WikiStarWars_Yellow
        }
    }
}