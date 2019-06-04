package com.emi.newsfeeding.di

import com.emi.newsfeeding.NetworkServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule{

    @Singleton @Provides
    @JvmStatic fun gsonConverter() : Gson = GsonBuilder()
        .setLenient()
        .create()

    @Singleton @Provides
    @JvmStatic fun httpClient() : OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY })
        .build()

    @Singleton @Provides
    @JvmStatic fun NetworkServices(retrofit: Retrofit) : NetworkServices{
        return retrofit.create(NetworkServices::class.java)
    }

    @Singleton @Provides
    @JvmStatic fun NetworkProvider() : Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create(gsonConverter()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpClient())
        .build()

}