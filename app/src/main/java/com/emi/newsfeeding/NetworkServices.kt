package com.emi.newsfeeding

import io.reactivex.Observable
import retrofit2.http.GET

interface NetworkServices{

    @GET("top-headlines?country=us&apiKey=5b7d45a4569b49c4aef6b5bbea12fc23")
    fun getNewsData() : Observable<NewsResponse>

}