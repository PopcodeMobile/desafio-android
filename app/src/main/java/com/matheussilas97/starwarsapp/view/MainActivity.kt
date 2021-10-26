package com.matheussilas97.starwarsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.matheussilas97.starwarsapp.R
import com.matheussilas97.starwarsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val naviController = findNavController(R.id.nav_host)
        binding.bottomNavigationView.setupWithNavController(naviController)
    }
}