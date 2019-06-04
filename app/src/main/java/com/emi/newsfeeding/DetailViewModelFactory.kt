package com.emi.newsfeeding

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException


class DetailViewModelFactory(private var news : NewsFeed, private var context: Context) : ViewModelProvider.Factory{


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java))
            return DetailViewModel(news, context) as T
        throw IllegalArgumentException("Its not the model class")
    }


}