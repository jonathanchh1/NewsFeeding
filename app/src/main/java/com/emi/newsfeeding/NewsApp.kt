package com.emi.newsfeeding

import android.app.Application
import android.content.Context
import timber.log.Timber

class NewsApp : Application() {


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
