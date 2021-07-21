package com.example.starwarsapi.ui.splashActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.starwarsapi.R
import com.example.starwarsapi.ui.starWarsList.StarWarsListActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        Handler().postDelayed({
            val intent = Intent(this, StarWarsListActivity::class.java)
            startActivity(intent)
        }, 3000)

    }



}