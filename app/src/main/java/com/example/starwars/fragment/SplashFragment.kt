package com.example.starwars.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.starwars.R

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val view = inflater.inflate(R.layout.fragment_splash, container, false)

        // Temporizador
        Handler().postDelayed({
            // Verifica a condição do Shared
            if(onBoardindFinished()){
                findNavController().navigate(R.id.action_splashFragment_to_inicioFragment)
            }else {
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }
        },3000)

       return view
    }

    // Captura valor inicial do Shared
    private fun onBoardindFinished(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished",false)
    }

}