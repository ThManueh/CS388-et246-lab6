package com.codepath.articlesearch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.codepath.articlesearch.databinding.ActivityMainBinding
import kotlinx.serialization.json.Json

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val SELECTED_NAV_ITEM_KEY = "SELECTED_NAV_ITEM_KEY"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedFragmentId = savedInstanceState?.getInt(SELECTED_NAV_ITEM_KEY) ?: R.id.nav_home
        binding.bottomNavigation.selectedItemId = selectedFragmentId

        if (supportFragmentManager.findFragmentById(R.id.article_frame_layout) == null) {
            replaceFragment(getFragmentById(selectedFragmentId))
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            replaceFragment(getFragmentById(item.itemId))
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_NAV_ITEM_KEY, binding.bottomNavigation.selectedItemId)
    }

    private fun replaceFragment(fragment: Fragment) {
        Log.d(TAG, "Replacing fragment: $fragment")
        supportFragmentManager.beginTransaction()
            .replace(R.id.article_frame_layout, fragment)
            .commit()
    }

    private fun getFragmentById(itemId: Int): Fragment {
        return when (itemId) {
            R.id.nav_home -> HomeFragment()
            R.id.nav_books -> BestSellerBooksFragment()
            R.id.nav_articles -> ArticleListFragment()
            else -> HomeFragment()
        }
    }
}