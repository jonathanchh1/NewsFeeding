package com.emi.newsfeeding.di

import android.app.Activity


interface ComponentProvider {


    val appComponent : AppComponent
}

val Activity.injector get() = (application as ComponentProvider).appComponent