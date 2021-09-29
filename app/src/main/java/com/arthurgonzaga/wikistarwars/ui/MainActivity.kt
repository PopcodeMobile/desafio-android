package com.arthurgonzaga.wikistarwars.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.IdRes
import com.arthurgonzaga.wikistarwars.R
import com.arthurgonzaga.wikistarwars.ui.components.MyThemes
import com.arthurgonzaga.wikistarwars.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val vm : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getMyTheme())
        setContentView(R.layout.activity_main)

        vm.inSync.observe(this){
            if(it == true){
                Toast.makeText(this, R.string.sync_success, Toast.LENGTH_SHORT).show()
            }
        }
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