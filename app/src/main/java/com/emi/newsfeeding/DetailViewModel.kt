package com.emi.newsfeeding

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.format.DateUtils
import android.view.View
import android.webkit.WebSettings
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.emi.newsfeeding.Connectivity.Companion.isConnected
import kotlinx.android.synthetic.main.activity_detail.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.sign





class DetailViewModel @Inject constructor(private var news : NewsFeed, private var context: Context) : ViewModel() {


    fun thumbnail() = news.thumbnail
   // fun author() = news.author
    fun title() = news.title
  //  fun content() = news.content
    fun url() = news.url
    fun desc() = news.desc
    fun published() = manipulateDateFormat(news.published)

    fun content() : String?{
        return news.content ?: context.getString(R.string.unavailable_info)
    }

    fun manipulateDateFormat(post_date: String?): String? {
        val existingUTCFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var date: Date? = null
        try {
            date = existingUTCFormat.parse(post_date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (date != null) {
            // Converting timestamp into x ago format
            val timeAgo = DateUtils.getRelativeTimeSpanString(
                java.lang.Long.parseLong(String.format(date.time.toString())),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS
            )
            return timeAgo.toString() + ""
        } else {
            return post_date
        }
    }

    fun onReadMore(){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url()))
                Toast.makeText(context, "$ its all messages", Toast.LENGTH_SHORT).show()
                context.startActivity(intent)
             }
        }
