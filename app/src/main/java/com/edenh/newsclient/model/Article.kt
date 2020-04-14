package com.edenh.newsclient.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.edenh.newsclient.repository.DateConverter

@Entity(tableName = "article_table")
data class Article(
    @PrimaryKey @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "author") var author: String?,
    @ColumnInfo(name = "urlToImage") var urlToImage: String,
    @ColumnInfo(name = "publishedAt") @TypeConverters(DateConverter::class) var publishedAt: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "url") var url: String
)