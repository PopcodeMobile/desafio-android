package com.example.starwars_app.view


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.starwars_app.R
import com.example.starwars_app.model.api.StarWarsApi
import com.example.starwars_app.model.item.PersonItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_characters.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class CharactersActivity : AppCompatActivity() {

    private lateinit var tempPlanetSearch : ArrayList<PersonItem>
    var list = ArrayList<PersonItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)
        //setSupportActionBar(findViewById(R.id.toolbar))

        val charactersUrls = intent.getStringArrayListExtra("charactersUrls")
        val movieName = intent.getStringExtra("movieTitle")
        val api = StarWarsApi()
        val characterAdapter = GroupAdapter<ViewHolder>()



        txt_film_starship.text = "$movieName's Characters"

        rcv_characters.layoutManager = LinearLayoutManager(this)
        rcv_characters.adapter = characterAdapter
        rcv_characters.isNestedScrollingEnabled = false
        rcv_characters.isFocusable = false

        tempPlanetSearch = arrayListOf()

        if (charactersUrls != null) {
            api.loadCharacters(charactersUrls)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { character ->
                   /* if(loadingDialog.isShowing()== true)
                         loadingDialog.hideDialog()*/
                    list.add(PersonItem(character))
                    characterAdapter.clear()
                    characterAdapter.addAll(list)
                    characterAdapter.notifyDataSetChanged()
                },{
                        e -> e.printStackTrace()
                },{
                    characterAdapter.notifyDataSetChanged()
                })
        }
        Glide.with(this).load(R.drawable.background).centerCrop()
            .into(object : SimpleTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    mainLayout.background = resource
                }
            })

        tempPlanetSearch.addAll(list)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
       menuInflater.inflate(R.menu.menu_search, menu)
        val itemCharacter = menu?.findItem(R.id.item_search)
        val searchViewCharacter = itemCharacter?.actionView as SearchView
        searchViewCharacter.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")}

            override fun onQueryTextChange(newText: String?): Boolean {
                tempPlanetSearch.clear()
                val searchViewText: String? = newText?.toLowerCase(Locale.getDefault())
                if (searchViewText != null) {
                    if (searchViewText.isNotEmpty()) {
                        list.forEach {
                            tempPlanetSearch.clear()
                            tempPlanetSearch.addAll(list)
                            rcv_characters.adapter?.notifyDataSetChanged()
                        }
                    }
                }
            return true
            }
           })
                return super.onCreateOptionsMenu(menu)
    }
}


