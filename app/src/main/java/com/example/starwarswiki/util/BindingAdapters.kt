package com.example.starwarswiki.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarswiki.domain.PersonModel
import com.example.starwarswiki.viewmodel.PersonListAdapter

@BindingAdapter("shortDetails")
fun TextView.setShortDetails(item: PersonModel?){
    item?.let{
        text = formatShortDetails(it, resources)
    }
}

@BindingAdapter("longDetails")
fun TextView.setLongDetails(item: PersonModel?){
    item?.let{
        text = formatLongDetails(it, resources)
    }
}