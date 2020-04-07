package com.edenh.newsclient.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.edenh.newsclient.R
import com.edenh.newsclient.utils.GENERAL_FAILURE
import com.edenh.newsclient.utils.NETWORK_FAILURE
import com.edenh.newsclient.view.adapters.ArticlesAdapter
import com.edenh.newsclient.viewModel.ArticlesViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    // View
    private var articlesList: RecyclerView? = null
    private var articlesLoader: ProgressBar? = null

    //Components
    private var adapter: ArticlesAdapter? = null

    private var articlesViewModel: ArticlesViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
    }

    override fun onResume() {
        super.onResume()
        articlesViewModel?.fetchArticles()
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
        articlesViewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)
        articlesViewModel?.getArticlesLiveData()?.observe(this, Observer {
            if (it != null) {
                articlesLoader?.visibility = View.GONE
                articlesList?.visibility = View.VISIBLE

                //adapter init
                if (adapter == null) {
                    adapter = ArticlesAdapter(it)
                    articlesList?.adapter = adapter
                } else {
                    adapter?.articlesData = it
                }

            } else {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Something went wrong...",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })

        articlesViewModel?.getErrorLiveData()?.observe(this, Observer {
            articlesLoader?.visibility = View.GONE
            articlesList?.visibility = View.GONE

            when (it) {
                GENERAL_FAILURE -> "Something went wrong..."
                NETWORK_FAILURE -> "Connection with the server failed..."
                else -> null
            }?.let { errorMessage ->
                Snackbar.make(
                    findViewById(android.R.id.content),
                    errorMessage,
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Retry") {
                    articlesLoader?.visibility = View.VISIBLE
                    articlesViewModel?.fetchArticles()
                }.show()
            }
        })

    }
}
