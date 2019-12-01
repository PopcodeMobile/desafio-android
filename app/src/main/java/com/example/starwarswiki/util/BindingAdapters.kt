package com.example.starwarswiki.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.example.starwarswiki.R
import com.example.starwarswiki.domain.PersonModel

@BindingAdapter("alternateColor")
fun ConstraintLayout.setAlternateColor(i: Int){
    if(i%2==1)
        setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
    else
        setBackgroundColor(resources.getColor(R.color.colorPrimary))
}

@BindingAdapter("shortDetails")
fun TextView.setShortDetails(item: PersonModel?){
    item?.let{
        text = formatShortDetails(it, resources)
    }
}

//@BindingAdapter("isFavorite")
//fun ImageView.setFavoriteStatus(item: PersonModel?){
//    item?.let{
//        if(item.isFavorite)
//            setImageResource(R.drawable.ic_star)
//    }
//}

@BindingAdapter("longDetails")
fun TextView.setLongDetails(item: PersonModel?){
    item?.let{
        text = formatLongDetails(it, resources)
    }
}