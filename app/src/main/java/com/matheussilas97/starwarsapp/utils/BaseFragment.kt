package com.matheussilas97.starwarsapp.utils

import android.text.TextUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class BaseFragment: Fragment() {

    fun showToast(msg: String){
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    fun setNoResultAdapter(recyclerView: RecyclerView, message: String) {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = NoResultAdapter(requireContext(), message)
        recyclerView.layoutManager = layoutManager
    }

}