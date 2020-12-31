package com.example.starwars.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.starwars.R
import com.example.starwars.view.Inicio

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
                val intent = Intent(requireContext(), Inicio::class.java)
                startActivity(intent)
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