package com.example.desafio_android.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafio_android.R
import com.example.desafio_android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.customToolbar)

    }
}