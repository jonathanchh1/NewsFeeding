package com.emi.newsfeeding

import android.app.Application
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
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
     private var likeCounter = ObservableInt(0)
     var isLoading : ObservableField<Boolean>
     var loadingApiNews : LiveData<List<NewsFeed>>
     var loadingDatabaseNews : LiveData<List<NewsFeed>>
     private var MutableLike = MutableLiveData<Int>()
     val storeLikes : LiveData<Int>
     get() = MutableLike

            init {

                val newsDao = NewsRoomDatabase.getDatabase(appContext(), viewModelScope).newsDao()
                 newsRepo = NewsRepo(newsDao, this, newsPathFinder)
                 loadingDatabaseNews = newsRepo.loadingFromDatabase
                 loadingApiNews = newsRepo.LoadingData()
                 isLoading = newsRepo.isLoading
                 MutableLike.value = likeCounter.get()

            }

    fun onLike(){
         likeCounter.set(likeCounter.get() + 1)
    }



    fun insertNews(news : NewsFeed) = viewModelScope.launch(Dispatchers.IO){
         newsRepo.insertRepo(news)
    }

}
