package com.codepath.articlesearch

interface DisplayableArticle : java.io.Serializable{
    val webUrl: String
    val title: String
    val abstract: String
    val mediaImageUrl: String
    val byline: String?
}