package com.emi.newsfeeding

import android.content.Context
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor(private var news : NewsFeed, private var context: Context) : ViewModel() {


    fun thumbnail() = news.thumbnail
   // fun author() = news.author
    fun title() = news.title
    fun content() = news.content
    fun url() = news.url
    fun published() = news.published


    fun author() : String? {
        return news.author ?: context.getString(R.string.unkown)
    }

    }