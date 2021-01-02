package com.example.starwars.presentation.fragment.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.starwars.R
import com.example.starwars.presentation.fragment.onboarding.screens.FirstScreen
import com.example.starwars.presentation.fragment.onboarding.screens.SecondScreen
import kotlinx.android.synthetic.main.fragment_view_pager.view.*

class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        // Anexa todos os Screens ao ArrayList
        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen()
        )

        // Declara e inicializa o adapter, passando todos os Screens que o fragmentList possui
        val adapter = ViewPagerAdapter(
            fragmentList,requireActivity().supportFragmentManager,
            lifecycle
        )

        // Anexa a viewPage o adapter inicializado
        view.viewPager.adapter = adapter

        return view
    }
}