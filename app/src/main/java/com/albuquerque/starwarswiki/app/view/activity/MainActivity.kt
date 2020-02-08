package com.albuquerque.starwarswiki.app.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.albuquerque.starwarswiki.R
import com.albuquerque.starwarswiki.app.extensions.openFragment
import com.albuquerque.starwarswiki.app.view.fragment.FavoritesFragment
import com.albuquerque.starwarswiki.app.view.fragment.PeopleFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var teste = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigation()

    }

    private fun setupBottomNavigation() {
        PeopleFragment().openFragment(supportFragmentManager, R.id.container)

        bottomNavigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.navigation_people -> {
                    PeopleFragment().openFragment(
                        supportFragmentManager,
                        R.id.container
                    )
                    teste = true
                    return@OnNavigationItemSelectedListener true
                }

                else -> {
                    FavoritesFragment().openFragment(
                        supportFragmentManager,
                        R.id.container
                    )
                    teste = false
                    return@OnNavigationItemSelectedListener true
                }
            }
        })

    }

}
