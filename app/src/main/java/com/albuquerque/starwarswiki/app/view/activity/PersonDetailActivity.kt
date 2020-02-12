package com.albuquerque.starwarswiki.app.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.albuquerque.starwarswiki.R
import com.albuquerque.starwarswiki.app.extensions.setupToolbar
import com.albuquerque.starwarswiki.app.model.ui.PersonUI
import kotlinx.android.synthetic.main.activity_person_detail.*

class PersonDetailActivity : AppCompatActivity() {

    companion object {
        const val PERSON = "PERSON"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)

        val person = (intent.extras!!.getSerializable(PERSON)!! as PersonUI)

        setupToolbar(toolbar, person.name) {
            setDisplayHomeAsUpEnabled(true)
        }


    }

}
