package com.example.starwars_app

import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget

class LoadingDialog(private val activity: Activity) {
    private var dialog: Dialog? = null

    fun showDialog() {

        dialog = Dialog(activity)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.custom_loading_layout)

        val gifImageView = dialog?.findViewById<ImageView>(R.id.mgv_loading)
        val imageViewTarget = DrawableImageViewTarget(gifImageView)

        Glide.with(activity)
            .load(R.drawable.loading_t6shuttle)
            .centerCrop()
            .into(imageViewTarget)

       dialog?.show()
    }
    fun hideDialog() {
        dialog!!.dismiss()
    }
    fun isShowing():Boolean?{
        return dialog?.isShowing
    }
    fun finishDialog(){
    }
}