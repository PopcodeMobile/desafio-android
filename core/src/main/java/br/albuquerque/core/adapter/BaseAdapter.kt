package br.albuquerque.core.adapter

import androidx.recyclerview.widget.RecyclerView
import br.albuquerque.core.holder.BaseViewHolder

abstract class BaseAdapter<T, Holder: BaseViewHolder<T>>: RecyclerView.Adapter<Holder>() {

    var items: List<T> = listOf()

    open fun refresh(items: List<T>){
        this.items = items
        notifyDataSetChanged()
    }

    open fun getItemAt(index: Int): T {
        return items[index]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

}