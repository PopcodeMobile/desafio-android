package br.com.starwarswiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.starwarswiki.services.SWServiceApi

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SWServiceApi.getPeople { person, error ->
            println("person: $person")
            println("person error: $error")
        }

        SWServiceApi.getPlanets { planet, error ->
            println("planet: $planet")
            println("planet error: $error")
        }

        SWServiceApi.getSpecies { specie, error ->
            println("specie: $specie")
            println("specie error: $error")
        }
    }
}
