package com.emi.newsfeeding

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.emi.newsfeeding.NewsApp.Companion.appContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

 class NewsViewModel @Inject constructor(newsPathFinder: NewsPathFinder) : AndroidViewModel(Application()){

     private var newsRepo : NewsRepo
     var isLoading : ObservableField<Boolean>
     var loadingApiNews : LiveData<List<NewsFeed>>
     var loadingDatabaseNews : LiveData<List<NewsFeed>>
     private var mutableLike = MutableLiveData<Int>(R.integer.like)
      val storeLikes : LiveData<Int>
        get() = mutableLike

            init {

                val newsDao = NewsRoomDatabase.getDatabase(appContext(), viewModelScope).newsDao()
                 newsRepo = NewsRepo(newsDao, this, newsPathFinder)
                 loadingDatabaseNews = newsRepo.loadingFromDatabase
                 loadingApiNews = newsRepo.LoadingData()
                 isLoading = newsRepo.isLoading
            }

    fun onLike(){
        mutableLike.value = (mutableLike.value ?: 0) + 1
    }


    fun insertNews(news : NewsFeed) = viewModelScope.launch(Dispatchers.IO){
         newsRepo.insertRepo(news)
    }

}
