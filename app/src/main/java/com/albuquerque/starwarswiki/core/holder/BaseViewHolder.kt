package com.albuquerque.starwarswiki.core.holder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.albuquerque.movietvshow.core.adapter.BindableView


abstract class BaseViewHolder<T>(var binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root), BindableView<T>{
    abstract override fun bind(item: T)
}