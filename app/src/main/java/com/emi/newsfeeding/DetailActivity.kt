package com.emi.newsfeeding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.CollapsingToolbarLayout

class DetailActivity : AppCompatActivity() {

    private lateinit var appLayout : CollapsingToolbarLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        appLayout = findViewById(R.id.toolbar_layout)

    }

    private fun setUpbar(){
        appLayout?.elevation= 7.0f

    }
}
