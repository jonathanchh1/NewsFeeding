package com.emi.newsfeeding

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import javax.inject.Singleton

@Entity(tableName = "table_news")
@Parcelize
@Singleton data class NewsFeed(@PrimaryKey(autoGenerate = true) var id : Int = 0,
                                 @Expose
                                 @SerializedName("author")
                                 @ColumnInfo(name = "author") var author : String?=null,
                                 @Expose
                                 @SerializedName("title")
                                 @ColumnInfo(name = "title") var title : String?=null,
                                 @Expose
                                 @SerializedName("description")
                                 @ColumnInfo(name = "description") var desc : String?=null,
                                 @Expose
                                 @SerializedName("url")
                                 @ColumnInfo(name = "url") var url : String?=null,
                                 @Expose
                                 @SerializedName("urlToImage")
                                 @ColumnInfo(name = "urlToImage") var thumbnail : String?=null,
                                 @Expose
                                 @SerializedName("publishedAt")
                                 @ColumnInfo(name = "published") var published : String?=null,
                                 @Expose
                                 @SerializedName("content")
                                 @ColumnInfo(name = "content") var content : String?=null,
                                 @ColumnInfo(name = "likes") var likes : Int?=null) : Parcelable


@Entity(tableName = "tables_news")
@Parcelize
@Singleton data class Source(@Expose
                             @SerializedName("id")
                             @PrimaryKey(autoGenerate = true) var id : Int?=null,
                             @Expose
                             @SerializedName("name")
                             @ColumnInfo(name = "name") var name : String?=null) : Parcelable