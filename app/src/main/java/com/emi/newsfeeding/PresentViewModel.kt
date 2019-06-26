package com.emi.newsfeeding

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Pair.create
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import timber.log.Timber
import javax.inject.Inject

class PresentViewModel constructor(private var context : Context, var news : NewsFeed) : ViewModel(){


    val isLiked = ObservableField<Boolean>(false)
    fun thumbnail() = news.thumbnail
    fun desc() = news.desc
    fun title() = news.title
    fun likes() = news.likes


    fun onLiked(){
        if(news.likes.get() == 0){
            isLiked.set(false)
            news.likes.set(news.likes.get() + 1)
        }else if(news.likes.get() > 0){
            isLiked.set(true)
            news.likes.set(1)
        }
    }



    fun onClick(view : View){
        val intent = Intent(view.context, DetailActivity::class.java)
        intent.putExtra("news", news)
        transition(view, intent)
    }

    fun SharedActionProvider(){
        val intent = Intent(Intent.ACTION_SEND)
         intent.type = "text/plain"
         intent.putExtra(Intent.EXTRA_SUBJECT, news.title)
         intent.putExtra(Intent.EXTRA_TEXT, news.url)
         context.startActivity(Intent.createChooser(intent, "Shared Options"))
    }



    private fun transition(view : View, intent : Intent){
        val thumbnail = view.rootView.findViewById<ImageView>(R.id.thumbnail)
        val title = view.rootView.findViewById<TextView>(R.id.title)
        val placeHolder = view.rootView.findViewById<RelativeLayout>(R.id.title_placeholder)
        val status = view.rootView.findViewById<View>(android.R.id.statusBarBackground)
        val navigationBar = view.rootView.findViewById<View>(android.R.id.navigationBarBackground)

        val thumbnailPair = create(thumbnail as View, view.context.getString(R.string.viewTrans))
        val titlePair = create(title as View, view.context.getString(R.string.viewTrans))
        val placeholderPair = create(placeHolder as View, view.context.getString(R.string.placeHolder))
     //   val toolbarPair = create(view.rootView.detail_toolbar as View,  view.context.getString(R.string.toolbarTrans))
        val statusPair = create(status, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME)
        val naviPairs = create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME)
        val pairs= mutableListOf(thumbnailPair, titlePair, placeholderPair, statusPair)

        if(naviPairs != null && navigationBar != null){
            pairs  += naviPairs
        }

        val options = ActivityOptions.makeSceneTransitionAnimation(context as Activity, thumbnailPair, titlePair, placeholderPair)
        ActivityOptions.makeSceneTransitionAnimation(context as Activity, *pairs.toTypedArray())
        ContextCompat.startActivity(context, intent, options.toBundle())
    }
}