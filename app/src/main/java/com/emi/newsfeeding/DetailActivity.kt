package com.emi.newsfeeding

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.emi.newsfeeding.databinding.ActivityDetailBinding
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.detail_toolbar.*


class DetailActivity : AppCompatActivity() {

    private lateinit var factory : DetailViewModelFactory
    private lateinit var detailViewModels : DetailViewModel
    lateinit var news : NewsFeed
    private var DefaultColor : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
         news = intent.getParcelableExtra("news") as NewsFeed
         factory = DetailViewModelFactory(news, this)
         detailViewModels = ViewModelProviders.of(this, factory).get(DetailViewModel::class.java)
         binding.detailViewModel = detailViewModels
         binding.lifecycleOwner = this
         DefaultColor = ContextCompat.getColor(this, R.color.colorPrimary)

        pictureColor()
        setUpbar()
    }

    private fun setUpbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
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
        title_placeholder.setBackgroundColor(palette.getLightVibrantColor(DefaultColor))
        article_layout.setBackgroundColor(palette.getMutedColor(DefaultColor))
        description_placeholder.setBackgroundColor(palette.getLightMutedColor(DefaultColor))
    }



}
