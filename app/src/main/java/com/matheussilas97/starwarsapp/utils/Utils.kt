package com.matheussilas97.starwarsapp.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.matheussilas97.starwarsapp.R
import com.matheussilas97.starwarsapp.databinding.DialogLoadingBinding
import com.matheussilas97.starwarsapp.databinding.DialogWarningBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object Utils {

    fun showLoading(context: Context, message: Int): AlertDialog {
        val builder = AlertDialog.Builder(context)
        val binding: DialogLoadingBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_loading, null, false
        )
        builder.setView(binding.root)
        val dialog = builder.show()
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.txtMessage.setText(message)
        return dialog
    }

    fun showDialogWarning(img: Int, text: String, context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            val builder = AlertDialog.Builder(context)
            val binding: DialogWarningBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.dialog_warning, null, false
            )
            builder.setView(binding.root)
            val dialog = builder.show()
            dialog.setCancelable(false)
            binding.txtWarning.text = text
            binding.imgFlag.setImageResource(img)

            delay(3000)
            dialog.dismiss()
        }
    }
}