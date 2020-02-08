package com.albuquerque.starwarswiki.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun Fragment.openFragment(supportFragmentManager: FragmentManager, container: Int){
    supportFragmentManager.beginTransaction().replace(container, this).commit()
}