package com.emi.newsfeeding

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.emi.newsfeeding.Connectivity.Companion.isConnected
import com.emi.newsfeeding.databinding.ActivityDetailBinding
import com.emi.newsfeeding.generated.callback.OnClickListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.view.*
import kotlinx.android.synthetic.main.detail_toolbar.*
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import timber.log.Timber
import java.io.IOException


class DetailActivity : AppCompatActivity() {

    private lateinit var factory: DetailViewModelFactory
    private lateinit var detailViewModels: DetailViewModel
    lateinit var news: NewsFeed
    private var DefaultColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        news = intent.getParcelableExtra("news") as NewsFeed
        factory = DetailViewModelFactory(news, this)
        detailViewModels = ViewModelProviders.of(this, factory).get(DetailViewModel::class.java)
        binding.detailViewModel = detailViewModels
        binding.lifecycleOwner = this
        DefaultColor = ContextCompat.getColor(this, R.color.colorPrimary)
        pictureColor()
        setUpbar()
    }



    /*
       @TargetApi(Build.VERSION_CODES.O)
       private fun WebSetting(webView: WebView) {
        val websetting = webView.settings
        websetting.cacheMode = WebSettings.LOAD_DEFAULT
        websetting.setAppCachePath(applicationContext.cacheDir.toString())
        websetting.setAppCacheEnabled(true)
        webView.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_WAIVED, true)
        webView.setNetworkAvailable(isConnected(applicationContext))
        webView.webViewClient = ViewClient(applicationContext)
           webView.isNestedScrollingEnabled = true
           webView.loadUrl(detailViewModels.url())
       }

*/



    private fun setUpbar(){
        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun pictureColor(){
      BindingMethod.bitmap.observe(this, Observer {
          if(it != null){
              val pic = Palette.from(it).generate()
              applyPalette(pic)
          }
      })
    }


    private fun applyPalette(palette : Palette){
        window.setBackgroundDrawable(ColorDrawable(palette.getDarkMutedColor(DefaultColor)))
        title_placeholder.setBackgroundColor(palette.getDarkVibrantColor(DefaultColor))
        readmoreButton.setBackgroundColor(palette.getDarkVibrantColor(DefaultColor))
        description_placeholder.setBackgroundColor(palette.getDarkMutedColor(DefaultColor))
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
