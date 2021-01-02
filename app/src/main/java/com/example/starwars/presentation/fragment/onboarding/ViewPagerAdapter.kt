package com.example.starwars.presentation.fragment.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    list: ArrayList<Fragment>,
    fm: FragmentManager,
    lifecycle: Lifecycle
)
    : FragmentStateAdapter(fm, lifecycle){

    // Declara valor como uma List
    private val fragmentList = list

    // Retorna o tamanho da List
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    // Obtem posição da List
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}