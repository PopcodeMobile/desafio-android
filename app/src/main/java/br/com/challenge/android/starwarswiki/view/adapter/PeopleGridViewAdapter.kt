package br.com.challenge.android.starwarswiki.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.challenge.android.starwarswiki.R
import br.com.challenge.android.starwarswiki.model.domain.Person
import br.com.challenge.android.starwarswiki.view.PersonItemClickListener

class PeopleGridViewAdapter(
    private val inflaterFromAppContext: LayoutInflater,
    private var people: ArrayList<Person>,
    private val callback: PersonItemClickListener
): BaseAdapter() {

    fun refreshData(people: ArrayList<Person>) {
        this.people = people
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return people.size
    }

    override fun getItem(p0: Int): Person {
        return people[p0]
    }

    override fun getItemId(p0: Int): Long {
        return getItem(p0).name.hashCode().toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val viewHolderItem: PersonInGridViewHolderItem
        var view = p1

        if(p1 == null) {
            view = inflaterFromAppContext.inflate(R.layout.grid_item_person, null)
            viewHolderItem = PersonInGridViewHolderItem(view)

        } else {
            viewHolderItem = p1.tag as PersonInGridViewHolderItem
        }

        val item = getItem(p0)

        viewHolderItem.personNameInItem.text = item.name
        viewHolderItem.personHeightInItem.text = item.height
        viewHolderItem.personGenderInItem.text = item.gender
        viewHolderItem.personMassInItem.text = item.mass

        Log.d("DebugPerson","Inside PeopleGridViewAdapter, viewHolderItem name text: ${viewHolderItem.personNameInItem.text}")

        view!!.setOnClickListener{
            callback.onClick(item)
        }

        view.tag = viewHolderItem

        return view
    }

}