package com.emi.newsfeeding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.emi.newsfeeding.databinding.NewsItemsBinding
import javax.inject.Inject

class NewsListAdapter @Inject constructor(private var newsList : List<NewsFeed>) : RecyclerView.Adapter<NewsListAdapter.NewsFeedBindingView>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedBindingView {
        val binding : NewsItemsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.news_items, parent, false)
        return NewsFeedBindingView(binding)
    }

    override fun onBindViewHolder(holder: NewsFeedBindingView, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }


    internal fun updateAdapter(newsfeed : List<NewsFeed>){
        this.newsList = newsfeed
    }

    inner class NewsFeedBindingView(var binding : NewsItemsBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(news : NewsFeed){
            if(binding.newsModel == null){
                binding.newsModel = PresentViewModel(news)
            }else{
                binding.newsModel!!.news = news
            }
        }
    }
}