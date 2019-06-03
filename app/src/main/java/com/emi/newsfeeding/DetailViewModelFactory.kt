package com.emi.newsfeeding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException


class DetailViewModelFactory(private var news : NewsFeed) : ViewModelProvider.Factory{


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java))
            return DetailViewModel(news) as T
        throw IllegalArgumentException("Its not the model class")
    }


}