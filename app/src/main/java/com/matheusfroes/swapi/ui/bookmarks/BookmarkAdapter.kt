package com.matheusfroes.swapi.ui.bookmarks

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.matheusfroes.swapi.R
import com.matheusfroes.swapi.data.model.Person
import com.matheusfroes.swapi.ui.peoplelist.PersonClickEvent
import com.matheusfroes.swapi.ui.peoplelist.ToggleBookmarkPersonEvent
import kotlinx.android.synthetic.main.person_view.view.*

class BookmarkAdapter
    : RecyclerView.Adapter<BookmarkAdapter.PeopleViewHolder>() {

    var personClickEvent: PersonClickEvent? = null
    var toggleBookmarkPersonEvent: ToggleBookmarkPersonEvent? = null

    var items = listOf<Person>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PeopleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_view, parent, false)
        return PeopleViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val person = items[position]

        holder.bind(person)
    }

    inner class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.ivBookmark.setOnClickListener {
                toggleBookmarkPersonEvent?.invoke(items[adapterPosition].id)
            }

            itemView.setOnClickListener {
                personClickEvent?.invoke(items[adapterPosition].id)
            }
        }

        fun bind(person: Person?) {
            if (person != null) {
                itemView.tvPersonName.text = person.name
                itemView.tvPersonGender.text = person.gender
                itemView.tvPersonHeight.text = person.height
                itemView.tvPersonMass.text = person.mass

                if (person.isBookmarked) {
                    itemView.ivBookmark.setImageResource(R.drawable.ic_bookmarked)
                } else {
                    itemView.ivBookmark.setImageResource(R.drawable.ic_bookmark)
                }
            }
        }
    }

    companion object {
        val PERSON_DIFF = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
                return oldItem == newItem
            }
        }
    }
}