package com.emi.newsfeeding

import io.reactivex.Observable
import retrofit2.http.GET

interface NetworkServices{

    @GET("placeholder")
    fun getNewsData() : Observable<NewsResponse>

}