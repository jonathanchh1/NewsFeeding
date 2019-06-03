package com.emi.newsfeeding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object BindingMethod {

    @BindingAdapter("bind:srcCompat")
    @JvmStatic fun loadImage(view : ImageView, url : String?){
        Picasso.with(view.context).load(url)
            .placeholder(R.drawable.placeholder)
            .centerCrop().into(view)
    }
}