package com.codepath.articlesearch

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SearchNewsResponse(
    @SerialName("response")
    val response: BaseResponse?
)

@Keep
@Serializable
data class BaseResponse(
    @SerialName("docs")
    val docs: List<Article>?
)

@Keep
@Serializable
data class Article(
    @SerialName("web_url")
    override val webUrl: String,
    @SerialName("pub_date")
    val pubDate: String?,
    @SerialName("headline")
    val headline: HeadLine?,
    @SerialName("multimedia")
    val multimedia: List<MultiMedia>?,
    @SerialName("abstract")
    override val abstract: String,
    @SerialName("byline")
    val bylineObject: Byline?
) : DisplayableArticle {
    override val title: String
        get() = headline?.main.orEmpty()
    override val byline: String?
        get() = bylineObject?.original
    override val mediaImageUrl: String
        get() = "https://www.nytimes.com/${multimedia?.firstOrNull { it.url != null }?.url.orEmpty()}"
}

@Keep
@Serializable
data class HeadLine(
    @SerialName("main")
    val main: String
) : java.io.Serializable

@Keep
@Serializable
data class Byline(
    @SerialName("original")
    val original: String? = null
) : java.io.Serializable

@Keep
@Serializable
data class MultiMedia(
    @SerialName("url")
    val url: String?
) : java.io.Serializable