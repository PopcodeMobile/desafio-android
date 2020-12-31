package com.example.starwars.fragment.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.starwars.R
import kotlinx.android.synthetic.main.fragment_first_screen.view.*
import kotlinx.android.synthetic.main.fragment_view_pager.*

class FirstScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_first_screen, container, false)

        // Obtendo a referencia XML do viewPager2
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        // Se o textView for clicado é passado para proxima Screen
        view.seguir1.setOnClickListener{
            viewPager?.currentItem = 1
        }

        return view
    }
}