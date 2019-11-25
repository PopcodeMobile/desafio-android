package com.example.starwarswiki.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.starwarswiki.domain.PersonModel

@BindingAdapter("shortDetails")
fun TextView.setShortDetails(item: PersonModel?){
    item?.let{
        text = formatShortDetails(item, resources)
    }
}