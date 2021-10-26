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

    fun openCalendar(editText: EditText, context: Context){
        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat)
            editText.setText(sdf.format(cal.time))

        }

        DatePickerDialog(context, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    open fun convertDateFormat(
        date: String?,
        initDateFormat: String?,
        endDateFormat: String?
    ): String? {
        return try {
            val initDate = SimpleDateFormat(initDateFormat).parse(date)
            val formatter = SimpleDateFormat(endDateFormat)
            formatter.format(initDate)
        } catch (e: ParseException) {
            e.printStackTrace()
            "Erro ao obter data"
        }
    }

    fun modelToJson(model: Any): String = try {
        GsonBuilder().setPrettyPrinting().create().toJson(model)
    } catch (e: Exception) {
        "Erro ao gerar Json"
    }

    fun isEmailValid(email: String?): Boolean {
        if (email.isNullOrEmpty())
            return false

        return android
            .util
            .Patterns
            .EMAIL_ADDRESS
            .matcher(email)
            .matches()
    }

    fun isCPF(document: String): Boolean {
        if (TextUtils.isEmpty(document)) return false

        val numbers = arrayListOf<Int>()

        document.filter { it.isDigit() }.forEach {
            numbers.add(it.toString().toInt())
        }

        if (numbers.size != 11) return false

        //repeticao
        (0..9).forEach { n ->
            val digits = arrayListOf<Int>()
            (0..10).forEach { digits.add(n) }
            if (numbers == digits) return false
        }

        //digito 1
        val dv1 = ((0..8).sumBy { (it + 1) * numbers[it] }).rem(11).let {
            if (it >= 10) 0 else it
        }

        val dv2 = ((0..8).sumBy { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
            if (it >= 10) 0 else it
        }

        return numbers[9] == dv1 && numbers[10] == dv2
    }
}