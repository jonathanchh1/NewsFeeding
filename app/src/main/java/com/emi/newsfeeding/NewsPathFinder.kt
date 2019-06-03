package com.emi.newsfeeding

import javax.inject.Inject


class NewsPathFinder @Inject constructor(private var services : NetworkServices){

    fun getFoundData() = services.getNewsData()

}