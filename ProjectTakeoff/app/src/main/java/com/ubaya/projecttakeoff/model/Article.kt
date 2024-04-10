package com.ubaya.projecttakeoff.model

data class Article(
    var id: String,
    var title: String,
    var content: String,
    var summary: String,
    var image_url: String,
    var author_id: String,
    var author_name: String,
)
