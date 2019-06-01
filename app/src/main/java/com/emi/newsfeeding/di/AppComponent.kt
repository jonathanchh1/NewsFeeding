package com.emi.newsfeeding.di

import android.content.Context
import com.emi.newsfeeding.NewsViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun applicationContext(application : Context) : Builder
        fun build() : AppComponent
    }

    fun NewsViewModelFactory() : ViewModelFactory<NewsViewModel>
}