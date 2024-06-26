package com.ubaya.projecttakeoff.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Article(
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "content")
    var content: String,
    @ColumnInfo(name = "summary")
    var summary: String,
    @ColumnInfo(name = "image_url")
    var image_url: String,
    @ColumnInfo(name = "author_id")
    var author_id: String,
    @ColumnInfo(name = "author_name")
    var author_name: String?,
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
