package com.edenh.newsclient.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.edenh.newsclient.model.Article

@Dao
interface ArticleDao {

    @Query("SELECT * from article_table")
    fun getArticles(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(articles: List<Article>)

}