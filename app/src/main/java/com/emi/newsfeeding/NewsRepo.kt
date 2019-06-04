package com.emi.newsfeeding

import android.app.Application
import android.content.Context
import androidx.annotation.WorkerThread
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class NewsRepo @Inject constructor(private var newsFeedDao: NewsFeedDao, private var newsViewModel: NewsViewModel, private var
newsPathFinder: NewsPathFinder) : Disposable {


    private var disposable = Disposables.empty()
    private val compositeDisposable = CompositeDisposable()
    private var mutableNewsApi = MutableLiveData<List<NewsFeed>>()
    var isLoading = ObservableField<Boolean>()
    var loadingFromDatabase : LiveData<List<NewsFeed>> = newsFeedDao.getDatabaseNews()

    private val loadingFromApi : LiveData<List<NewsFeed>>
    get() = mutableNewsApi

     fun LoadingData() : LiveData<List<NewsFeed>>{
         isLoading.set(false)
         disposable = newsPathFinder.getFoundData()
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe(this::onSuccessfulRequest, this::onLoadingError)
             compositeDisposable.add(disposable)
         return loadingFromApi
     }

    private fun onSuccessfulRequest(newsResponse: NewsResponse){
        isLoading.set(false)
        val result = newsResponse?.result
        mutableNewsApi.value = result
        result?.forEach {
            loadDatabase ->
            newsViewModel.insertNews(loadDatabase)
        }
        printValues(result)
    }

    private fun printValues(list : List<NewsFeed>?){
        list?.let {
            listdata ->
            Timber.e(NewsRepo::class.java.simpleName, R.string.succesful, listdata.toString())
        }
    }

    private fun onLoadingError(throwable: Throwable){
        isLoading.set(true)
        mutableNewsApi.value = null
        Timber.e(NewsRepo::class.java.simpleName, R.string.errorloading, throwable.message)
    }

    @WorkerThread
    suspend fun insertRepo(news : NewsFeed){
        newsFeedDao.insert(news)
    }



    override fun dispose() {
        disposable.dispose()
    }



    override fun isDisposed(): Boolean {
        if(disposable.isDisposed){
            return true
        }
        return false
    }




}