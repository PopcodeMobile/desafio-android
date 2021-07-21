package com.example.starwarsapi.ui.starWarsInfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.example.starwarsapi.R
import kotlinx.android.synthetic.main.activity_starwars_info.*

class StarWarsInfoActivity : AppCompatActivity() {

    lateinit var viewModel: StarWarsInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starwars_info)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        viewModel = ViewModelProvider(this).get(StarWarsInfoViewModel::class.java)

        initUI()

    }

    private fun initUI() {
        val id = intent.extras?.get("id") as Int

        viewModel.getStarWarsInfo(id)

        viewModel.starWarsInfo.observe(this,{ starwars ->
            text_name_person_detail.text = starwars.name
            text_height_person_detail.text = "Altura: ${starwars.height}cm"
            text_mass_person_detail.text = "Peso: ${starwars.mass}kg"
            text_gender_person_detail.text = "Genero: ${starwars.gender}"
            text_hair_color_person_detail.text = "Cor do Cabelo: ${starwars.hair_color}"
            text_eye_color_person_detail.text = "Cor dos Olhos: ${starwars.eye_color}"
            text_skin_color_person_detail.text = "Cor da Pele: ${starwars.skin_color}"
            text_birth_year_person_detail.text = "Ano de Nascimento: ${starwars.birth_year}"

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.share ->{
                // compartilha itens da activity de detalhe
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, text_name_person_detail.text)
                    type = "text/plain"

                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)

                return true
            }


            else -> true
        }
    }

}