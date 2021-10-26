package com.matheussilas97.starwarsapp.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.matheussilas97.starwarsapp.databinding.ActivitySplashScreenBinding
import com.matheussilas97.starwarsapp.view.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GlobalScope.launch {
            delay(3000)
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }
}