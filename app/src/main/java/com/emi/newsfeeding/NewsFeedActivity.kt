package com.emi.newsfeeding

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.emi.newsfeeding.databinding.ActivityFeedsBinding
import com.emi.newsfeeding.di.injector
import com.google.android.material.snackbar.Snackbar

class NewsFeedActivity : AppCompatActivity() {

    private lateinit var snackbar : Snackbar
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private lateinit var adapter : NewsListAdapter
    private lateinit var menu : Menu
    private var isListView : Boolean = false

    private val viewModel by lazy {
        ViewModelProviders.of(this, injector.NewsViewModelFactory()).get(NewsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feeds)
        val binding : ActivityFeedsBinding = DataBindingUtil.setContentView(this, R.layout.activity_feeds)
         binding.viewModel = viewModel
         binding.lifecycleOwner = this
         staggeredGridLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
         binding.rcNews.layoutManager = staggeredGridLayoutManager
         adapter = NewsListAdapter(ArrayList())
         binding.rcNews.adapter = adapter
         isListView = true

    }



    private fun onLoadingError(){
        snackbar = Snackbar.make(rootView(), getString(R.string.errorloading), Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction(R.string.error, {

                })
        snackbar.show()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_toggle){
            toggle()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toggle(){
        if(isListView){
            showListView()
        }else{
            showGridView()
        }
    }

    private fun showListView(){
        staggeredGridLayoutManager.spanCount = 1
        val item = menu.findItem(R.id.action_toggle)
        item.setIcon(R.drawable.ic_action_list)
        item.title = getString(R.string.list)
        isListView = true
    }

    private fun showGridView(){
        staggeredGridLayoutManager.spanCount = 2
        val item = menu.findItem(R.id.action_toggle)
        item.setIcon(R.drawable.ic_action_grid)
        item.title = getString(R.string.grid)
        isListView = false
    }


    private fun rootView() : View = findViewById(android.R.id.content)
}
