package br.com.starwarswiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.starwarswiki.actions.ActionCreator
import br.com.starwarswiki.models.AppState
import com.github.raulccabreu.redukt.states.StateListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        StarWarsApplication.redukt.listeners.add(object : StateListener<AppState> {
            override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
                return newState != oldState
            }

            override fun onChanged(state: AppState) {
                println("App state: $state")
            }
        })

        ActionCreator.syncPeope()
        ActionCreator.syncPlanets()
        ActionCreator.syncSpecies()
    }
}
