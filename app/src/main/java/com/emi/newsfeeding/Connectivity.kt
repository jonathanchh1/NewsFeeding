package com.emi.newsfeeding

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


class Connectivity{

    companion object{
        fun isConnected(context : Context) : Boolean{
            val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeInfo : NetworkInfo = connectivityManager.activeNetworkInfo
            return activeInfo != null && activeInfo.isConnected
        }
    }
}