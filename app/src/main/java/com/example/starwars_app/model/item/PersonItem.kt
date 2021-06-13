package com.example.starwars_app.model.item

import com.bumptech.glide.Glide
import com.example.starwars_app.R
import com.example.starwars_app.model.Pessoa
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_people.view.*

class PersonItem(private val person: Pessoa): Item<ViewHolder>(){


    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txt_name_character.text = person.name
        viewHolder.itemView.txt_character_info1.text = "Born in "+person.birthYear
        viewHolder.itemView.txt_character_info2.text = person.height+" cm height , "+person.mass+" kg"
        viewHolder.itemView.txt_characterinfo3.text = person.skinColor+" skin, "+person.hairColor+" hair, "+person.eyeColor+" eyes"

        //Se o nome for um desses descritos, coloca uma imagem individual. Se não, usa um placeholder.
        val drawable =  when(person.name){
            "Luke Skywalker"-> "https://cdn1.newsplex.pt/media/2018/12/4/668548.jpg?type=artigo"
            "C-3PO" ->"https://cdn.images.express.co.uk/img/dynamic/36/590x/secondary/Star-Wars-C3PO-is-shocked-by-the-foul-language-964367.jpg"
            "R2-D2" ->"https://images-na.ssl-images-amazon.com/images/I/71wGyqhM9yL._SY606_.jpg"
            "Darth Vader" -> "https://upload.wikimedia.org/wikipedia/pt/thumb/5/5c/DVader.jpeg/225px-DVader.jpeg"
            "Leia Organa"-> "https://i.kinja-img.com/gawker-media/image/upload/s--c-ZfGdaN--/c_scale,f_auto,fl_progressive,q_80,w_800/qsjzp4ryybdx6uo4t4aw.png"
            "Obi-Wan Kenobi" -> "https://upload.wikimedia.org/wikipedia/en/3/32/Ben_Kenobi.png"
            "Chewbacca" -> "https://thumbor.forbes.com/thumbor/711x474/https://specials-images.forbesimg.com/dam/imageserve/958761228/960x0.jpg?fit=scale"
            "Han Solo" -> "https://vignette.wikia.nocookie.net/pt.starwars/images/0/01/Hansoloprofile.jpg/revision/latest?cb=20120222133702"
            "Anakin Skywalker" -> "https://s3.amazonaws.com/libapps/accounts/81381/images/anakin-skywalker-before-darth-vader.jpg"
            "Yoda" -> "https://img1.looper.com/img/gallery/the-worst-things-yoda-has-ever-done/intro-1523455063.jpg"
            "Rey" -> "https://vignette.wikia.nocookie.net/pt.starwars/images/f/f8/ReyTLJEntertainmentWeeklyNovember.png/revision/latest?cb=20171222145527"
            else -> "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWotiGAzrbTsuczBduXTsmIo8OVT8AVdEoM78nTnPLKfsXqMra"
        }
        Glide.with(viewHolder.itemView).load(drawable).centerCrop().into(viewHolder.itemView.imageViewPerson)
    }
    override fun getLayout(): Int {
        return R.layout.row_people
    }
}