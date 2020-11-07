package br.com.challenge.android.starwarswiki

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import br.com.challenge.android.starwarswiki.model.domain.Person
import br.com.challenge.android.starwarswiki.view.PeopleListFragment
import br.com.challenge.android.starwarswiki.view.PersonItemClickListener

class MainActivity : AppCompatActivity(), PersonItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(findViewById<FrameLayout>(R.id.fragments_placeholder) != null) {
            if (savedInstanceState != null) {
                return
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.fragments_placeholder, PeopleListFragment(this))
                .commit()
        }
    }

    override fun onClick(person: Person) {
        if(findViewById<FrameLayout>(R.id.fragments_placeholder) != null) {

            /*supportFragmentManager.beginTransaction().replace(R.id.fragments_placeholder, MovieDetailsFragment(movie))
                .addToBackStack("Details")
                .commit()*/
        }
    }

}