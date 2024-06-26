package com.ubaya.projecttakeoff.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ArticleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg article: Article)

    @Query("SELECT * FROM article")
    fun selectAllArticle():List<Article>

    @Query("SELECT * FROM article " +
            "WHERE id = :id")
    fun selectArticle(id: Int): Article

    @Update
    fun update(article: Article)

    @Delete
    fun deleteArticle(vararg article: Article)
}