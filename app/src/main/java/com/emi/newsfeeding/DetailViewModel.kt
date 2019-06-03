package com.emi.newsfeeding

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor(private var news : NewsFeed) : ViewModel() {


    fun thumbnail() = news.thumbnail
    fun author() = news.author
    fun content() = news.content
    fun url() = news.url
    fun published() = news.published



}