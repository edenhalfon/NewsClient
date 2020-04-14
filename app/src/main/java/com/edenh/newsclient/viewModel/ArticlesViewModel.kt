package com.edenh.newsclient.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.edenh.newsclient.model.Article
import com.edenh.newsclient.repository.ArticlesRepository
import com.edenh.newsclient.repository.ArticlesRoomDatabase
import com.edenh.newsclient.utils.GENERAL_FAILURE
import com.edenh.newsclient.utils.NETWORK_FAILURE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ArticlesViewModel(application: Application) : AndroidViewModel(application) {

    private val articleRepository: ArticlesRepository
    private val errorLiveData: MediatorLiveData<String> = MediatorLiveData()
    private val articlesLiveData: MediatorLiveData<List<Article>> = MediatorLiveData()

    init {
        val articleDao = ArticlesRoomDatabase.getDatabase(application).articleDao()
        articleRepository = ArticlesRepository(articleDao)
    }

    fun getArticlesLiveData(): LiveData<List<Article>> {
        return articlesLiveData
    }

    fun getErrorLiveData(): LiveData<String?>? {
        return errorLiveData
    }

    fun fetchArticles() {
        articlesLiveData.addSource(articleRepository.getArticles()) {
            if (it != null) {
                articlesLiveData.value = it.articles
            }
        }

        val error = viewModelScope.async(Dispatchers.IO) {
            articleRepository.fetchAndInsertArticles()
        }

        viewModelScope.launch(Dispatchers.Main) {
            errorLiveData.addSource(error.await()) {
                val status: String = it?.status.toString()
                if (NETWORK_FAILURE == status
                    || GENERAL_FAILURE == status
                ) {
                    errorLiveData.value = status
                }
            }
        }
    }
}