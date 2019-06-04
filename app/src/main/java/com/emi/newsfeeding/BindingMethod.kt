package com.emi.newsfeeding

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.telecom.Call
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.palette.graphics.Palette
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


object BindingMethod {

    private var BitmapData = MutableLiveData<Bitmap>()
    val bitmap : LiveData<Bitmap>
    get() = BitmapData

    @BindingAdapter("bind:srcCompat")
    @JvmStatic fun loadImage(view : ImageView, url : String?){
        Picasso.with(view.context).load(url)
            .placeholder(R.drawable.placeholder).into(view, object : Callback{
                override fun onSuccess() {
                    val bitmap = (view.drawable as BitmapDrawable).bitmap
                      BitmapData.value = bitmap
               }

                override fun onError() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })

    }



}