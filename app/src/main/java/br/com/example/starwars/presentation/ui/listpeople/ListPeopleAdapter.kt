package br.com.example.starwars.presentation.ui.listpeople

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.example.starwars.domain.entities.People

class ListPeopleAdapter(
    private val callbackClick: (People) -> Unit
) : PagingDataAdapter<People, ListPeopleViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPeopleViewHolder {
        return ListPeopleViewHolder.inflate(parent, callbackClick)
    }

    override fun onBindViewHolder(holder: ListPeopleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<People>() {
            override fun areItemsTheSame(
                oldItem: People,
                newItem: People
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: People,
                newItem: People
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}