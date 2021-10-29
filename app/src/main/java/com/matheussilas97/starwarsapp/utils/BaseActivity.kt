package com.matheussilas97.starwarsapp.utils

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

open class BaseActivity : AppCompatActivity() {

    fun showToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun getNextActivity(activity: Class<*>) {
        startActivity(Intent(this, activity))
    }

    fun setNoResultAdapter(recyclerView: RecyclerView, message: String) {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = NoResultAdapter(this, message)
        recyclerView.layoutManager = layoutManager
    }

    fun modelToJson(model: Any): String = try {
        GsonBuilder().setPrettyPrinting().create().toJson(model)
    } catch (e: Exception) {
        "Erro ao gerar Json"
    }
}