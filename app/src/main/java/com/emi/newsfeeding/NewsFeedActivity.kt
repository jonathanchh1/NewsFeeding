package com.emi.newsfeeding

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionListenerAdapter
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.emi.newsfeeding.Connectivity.Companion.isConnected
import com.emi.newsfeeding.databinding.ActivityFeedsBinding
import com.emi.newsfeeding.di.injector
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.news_items.*
import kotlinx.android.synthetic.main.toolbar.*
import timber.log.Timber
import javax.inject.Inject

class NewsFeedActivity : AppCompatActivity() {

    private lateinit var snackbar : Snackbar
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private lateinit var adapter : NewsListAdapter
    private lateinit var menu : Menu
    private var isListView : Boolean = false
    private var news = NewsFeed()

    private val viewModel by lazy {
        ViewModelProviders.of(this, injector.NewsViewModelFactory()).get(NewsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feeds)
        val binding : ActivityFeedsBinding = DataBindingUtil.setContentView(this, R.layout.activity_feeds)
         binding.newsModels = viewModel
         binding.lifecycleOwner = this
         staggeredGridLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
         binding.rcNews.layoutManager = staggeredGridLayoutManager
         adapter = NewsListAdapter(ArrayList())
         binding.rcNews.adapter = adapter
         isListView = true

        viewModel.loadingApiNews.observe(this, Observer {
            if(it == null || it.isEmpty()){
                onLoadingError()
            }else{
                adapter.updateAdapter(it)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.storeLikes.observe(this, Observer{
           likes ->
            likes?.let {
                news.likes = likes
                Timber.e(NewsFeedActivity::class.java.simpleName, "likes :", it)

            }
        })


        setUpbar()
        windowTransition()
    }


    fun setUpbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.elevation = 7.5f
    }


    private fun onLoadingError(){
        snackbar = Snackbar.make(rootView(), getString(R.string.errorloading), Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction(R.string.error, ({
                    if(!isConnected(applicationContext)){
                        viewModel.loadingDatabaseNews.observe(this, Observer {
                            DatabaseData ->
                            DatabaseData?.let {
                                adapter.updateAdapter(it)
                                adapter.notifyDataSetChanged()
                            }
                        })
                    }
                })).show()
    }


   private fun windowTransition(){
        window.enterTransition.addListener(@TargetApi(Build.VERSION_CODES.O)
        object : TransitionListenerAdapter() {
            override fun onTransitionEnd(transition: Transition?) {
                super.onTransitionEnd(transition)
                 title_placeholder.visibility = View.VISIBLE
                 title_placeholder.animate().alpha(0.20f)
                 finishAfterTransition()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if(id == R.id.action_toggle){
            toggle()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toggle(){
        if(isListView){
            showGridView()
        }else{
            showListView()
        }
    }

    private fun showListView(){
        staggeredGridLayoutManager.spanCount = 1
        val item = menu.findItem(R.id.action_toggle)
        item.setIcon(R.drawable.ic_action_grid)
        item.title = getString(R.string.list)
        isListView = true
    }

    private fun showGridView(){
        staggeredGridLayoutManager.spanCount = 2
        val item = menu.findItem(R.id.action_toggle)
        item.setIcon(R.drawable.ic_action_list)
        item.title = getString(R.string.grid)
        isListView = false
    }


    private fun rootView() : View = findViewById(android.R.id.content)
}
