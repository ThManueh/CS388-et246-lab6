package com.codepath.articlesearch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "HomeFragment"
private const val ARTICLE_SEARCH_URL = "https://api.nytimes.com/svc/mostpopular/v2/viewed/7.json?api-key=${BuildConfig.API_KEY}"

class HomeFragment : Fragment() {
    private val articles = mutableListOf<DisplayableArticle>()
    private lateinit var articlesRecyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false).apply {
            articlesRecyclerView = findViewById(R.id.article_recycler_view)
            articlesRecyclerView.layoutManager = LinearLayoutManager(context)
            articleAdapter = ArticleAdapter(context, articles)
            articlesRecyclerView.adapter = articleAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchPopularArticles()
    }

    private fun fetchPopularArticles() {
        AsyncHttpClient().get(ARTICLE_SEARCH_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode - $response")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json")
                try {
                    val parsedJson = createJson().decodeFromString(
                        PopularNewsResponse.serializer(),
                        json.jsonObject.toString()
                    )
                    articles.apply {
                        clear()
                        addAll(parsedJson.results)
                    }
                    articleAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })
    }
}