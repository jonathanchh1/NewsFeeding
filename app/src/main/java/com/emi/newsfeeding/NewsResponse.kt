package com.emi.newsfeeding

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsResponse(@Expose
                         @SerializedName("status") var status : String?=null,
                         @Expose
                         @SerializedName("totalResults") var totalResult : Int?=null,
                      //    @Expose
                        //  @SerializedName("source") var sourcesData : String = "",
                         @Expose
                         @SerializedName("articles") var result : List<NewsFeed>?=null,
                         @Expose
                         @SerializedName("source") var sources : List<Source>?=null)