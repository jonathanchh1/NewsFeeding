package com.emi.newsfeeding

import android.app.Application
import android.content.Context
import com.emi.newsfeeding.di.AppComponent
import com.emi.newsfeeding.di.ComponentProvider
import com.emi.newsfeeding.di.DaggerAppComponent
import timber.log.Timber

class NewsApp : Application(), ComponentProvider {
    override val appComponent: AppComponent by lazy{
        DaggerAppComponent.builder()
            .applicationContext(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        setUpTimber()
    }

    init {
        instance = this
    }

    private fun setUpTimber(){
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object{
        private var instance : NewsApp?=null

        fun appContext() : Context {
            return instance!!.applicationContext
        }
    }
}
