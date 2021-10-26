package com.matheussilas97.starwarsapp.view.charactersdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.matheussilas97.starwarsapp.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewModel: CharactersDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CharactersDetailsViewModel::class.java]

        onClick()
        setDetails()
    }

    private fun setDetails() {
        viewModel.getDetails("1").observe(this, Observer {
            if (it!= null){
                binding.txtName.text = it.name
                binding.txtGender.text = it.gender
                binding.txtHeight.text = it.height
                binding.txtMass.text = it.mass
                binding.txtHairColor.text = it.hairColor
                binding.txtEyeColor.text = it.eyeColor
                binding.txtSkinColor.text = it.skinColor
                binding.txtYear.text = it.birthYear
                binding.txtHomeworld.text = ""
            }
        })
    }

    private fun onClick() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}