package com.example.starwars.fragment.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.starwars.R
import kotlinx.android.synthetic.main.fragment_second_screen.view.*

class SecondScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_second_screen, container, false)

        // Se textView for pressionada, o fragment Inicio sera aberta
        view.comecar.setOnClickListener{
            findNavController().navigate(R.id.action_viewPagerFragment_to_inicioFragment)
            onBoardingFinished()
        }

        return view

    }
    // Realiza o Set Bollean no Finished do Shared
    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }
}