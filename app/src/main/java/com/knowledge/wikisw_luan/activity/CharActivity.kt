package com.knowledge.wikisw_luan.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.knowledge.wikisw_luan.R
import com.knowledge.wikisw_luan.databinding.CharInfoBinding
import com.knowledge.wikisw_luan.models.CharacterModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharActivity : AppCompatActivity() {

    private lateinit var binding: CharInfoBinding
    private val charViewModel: CharViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.char_info)
        charViewModel.state.observe(this@CharActivity, {handleState(it)})

        val charInfo = intent.getParcelableExtra("CharInfo") as? CharacterModel
        binding = CharInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        charInfo?.let {
            binding.swName.text = it.name
            binding.swHeight.text = "Altura: ${it.height}"
            binding.swGender.text = "Gênero: ${it.gender}"
            binding.swMass.text = "Massa corpórea: ${it.mass}"
            binding.swHair.text = "Cor do cabelo: ${it.hairColor}"
            binding.swSkin.text = "Cor da pele: ${it.skinColor}"
            binding.swEye.text = "Cor dos olhos: ${it.eyeColor}"
            binding.swBday.text = "Ano de nascimento: ${it.birthYear}"
            binding.swHome.text = "Planeta de origem: ${it.homeWorld}"
            binding.swSpecie.text = "Espécie: ${it.species}"
            if (it.speciesId.isNotEmpty()) {
                charViewModel.getSpecies(it.speciesId)
            } else {
                binding.swSpecie.text = "Espécie não localizada."
            }
            if (it.homeWorldId.isNotEmpty()) {
                charViewModel.getPlanets(it.homeWorldId)
            } else {
                binding.swHome.text = "Planeta não localizado."
            }


            binding.swFavorite.setOnClickListener { _ ->
                it.isFavorite = !it.isFavorite
                charViewModel.getFav(it.name, it.isFavorite)
                if (it.isFavorite){
                    binding.swFavorite.setColorFilter(Color.RED)
                } else {
                    binding.swFavorite.setColorFilter(Color.GRAY)
                }
            }

            if (it.isFavorite){
                binding.swFavorite.setColorFilter(Color.RED)
            } else {
                binding.swFavorite.setColorFilter(Color.GRAY)
            }
        }
    }
    private fun handleState(state: Any) {
        when (state) {
            is SwState.ShowSpecieName -> {
                binding.swSpecie.text = "Raça: ${state.specieName}"

            }
//            is SwState.ShowPlanetName -> {
//                binding.swSpecie.text = "Planeta natal: ${state.planetName}"
//            }
        }

    }
}