package com.codepath.articlesearch

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PopularNewsResponse(
    val results: List<PopularArticle>
)

@Keep
@Serializable
data class PopularArticle(
    @SerialName("url")
    override val webUrl: String,
    @SerialName("published_date")
    val pubDate: String?,
    @SerialName("title")
    override val title: String,
    @SerialName("abstract")
    override val abstract: String,
    override val byline: String?,
    @SerialName("media")
    val media: List<PopularMedia> = emptyList()
) : DisplayableArticle {
    override val mediaImageUrl: String
        get() = media.firstOrNull()
            ?.mediaMetadata
            ?.firstOrNull { it.imageFormat == "mediumThreeByTwo440" || it.imageFormat == "large" }
            ?.url ?: media.firstOrNull()?.mediaMetadata?.firstOrNull()?.url ?: ""
}

@Keep
@Serializable
data class PopularMedia(
    @SerialName("media-metadata")
    val mediaMetadata: List<PopularMediaMetadata> = emptyList()
) : java.io.Serializable

@Keep
@Serializable
data class PopularMediaMetadata(
    val url: String,
    @SerialName("format") val imageFormat: String
) : java.io.Serializable