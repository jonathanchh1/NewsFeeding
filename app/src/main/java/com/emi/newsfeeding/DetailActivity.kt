package com.emi.newsfeeding

import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.emi.newsfeeding.databinding.ActivityDetailBinding
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.news_items.*

class DetailActivity : AppCompatActivity() {

    private lateinit var appLayout : CollapsingToolbarLayout
    private lateinit var factory : DetailViewModelFactory
    private lateinit var detailViewModels : DetailViewModel
    private lateinit var news : NewsFeed
    private var DefaultColor : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
         news = intent.getParcelableExtra("news") as NewsFeed
         factory = DetailViewModelFactory(news)
         detailViewModels = ViewModelProviders.of(this, factory).get(DetailViewModel::class.java)
         binding.detailViewModel = detailViewModels
         binding.lifecycleOwner = this
         appLayout = findViewById(R.id.toolbar_layout)
         DefaultColor = ContextCompat.getColor(this, R.color.colorPrimary)
         setUpbar()
    }

    private fun setUpbar(){
        appLayout?.elevation= 7.0f
        setSupportActionBar(detail_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }


    private fun getThumbnailPalette(){
        val bitmap = BitmapFactory.decodeResource(resources, Integer.valueOf(news?.thumbnail))
        val palette : Palette = Palette.from(bitmap).generate()
        applyPalette(palette)
    }

   private fun applyPalette(palette : Palette){
       window.setBackgroundDrawable(ColorDrawable(palette.getDarkMutedColor(DefaultColor)))
       thumbnail_placeholder.setBackgroundColor(palette.getMutedColor(DefaultColor))


    }


}
