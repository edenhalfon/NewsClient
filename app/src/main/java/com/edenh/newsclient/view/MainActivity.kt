package com.edenh.newsclient.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.edenh.newsclient.R
import com.edenh.newsclient.viewModel.ArticlesViewModel

class MainActivity : AppCompatActivity() {

    // View
    private var articlesList: RecyclerView? = null
    private var articlesLoader: ProgressBar? = null

    private var articlesViewModel: ArticlesViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
    }

    private fun initComponents() {
        findView()
        initViewModel()
    }

    private fun findView() {
        articlesLoader = findViewById(R.id.articles_loader)
        articlesList = findViewById(R.id.articles_list)
    }

    private fun initViewModel() {
        articlesViewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(ArticlesViewModel::class.java)

        articlesViewModel?.getArticlesLiveData()?.observe(this, Observer {
            if (it != null) {
                articlesLoader?.visibility = View.GONE
                articlesList?.visibility = View.VISIBLE

                //adapter init

            } else {
                // Show error with snackbar
            }
        })
    }
}
