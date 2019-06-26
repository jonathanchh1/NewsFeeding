package com.emi.newsfeeding

import android.app.Application
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.*
import com.emi.newsfeeding.NewsApp.Companion.appContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

 class NewsViewModel @Inject constructor(newsPathFinder: NewsPathFinder) : AndroidViewModel(Application()){

     private var newsRepo : NewsRepo
     val isLoading : ObservableField<Boolean>
     var loadingApiNews : LiveData<List<NewsFeed>>
     var loadingDatabaseNews : LiveData<List<NewsFeed>>
            init {

                val newsDao = NewsRoomDatabase.getDatabase(appContext(), viewModelScope).newsDao()
                 newsRepo = NewsRepo(newsDao, this, newsPathFinder)
                 loadingDatabaseNews = newsRepo.loadingFromDatabase
                 loadingApiNews = newsRepo.loadingFromApi
                 loadingApiNews = newsRepo.LoadingData()

                 isLoading = newsRepo.isLoading

            }


      fun onRefresh(){
          isLoading.set(false)
          newsRepo.LoadingData()
      }

     fun insertNews(news : NewsFeed) = viewModelScope.launch(Dispatchers.IO){
         newsRepo.insertRepo(news)
    }

}
