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

        // Set default or restored selected item
        val selectedFragmentId = savedInstanceState?.getInt(SELECTED_NAV_ITEM_KEY) ?: R.id.nav_home
        binding.bottomNavigation.selectedItemId = selectedFragmentId

        // Check if fragment is already loaded to avoid reloading on rotation
        if (supportFragmentManager.findFragmentById(R.id.article_frame_layout) == null) {
            val fragment = when (selectedFragmentId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_books -> BestSellerBooksFragment()
                R.id.nav_articles -> ArticleListFragment()
                else -> HomeFragment()
            }
            replaceFragment(fragment)
        }

        // Set up BottomNavigationView listener
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_books -> BestSellerBooksFragment()
                R.id.nav_articles -> ArticleListFragment()
                else -> HomeFragment()
            }
            replaceFragment(fragment)
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the currently selected navigation item ID with a consistent key
        outState.putInt(SELECTED_NAV_ITEM_KEY, binding.bottomNavigation.selectedItemId)
    }

    private fun replaceFragment(fragment: Fragment) {
        Log.d(TAG, "Replacing fragment: $fragment")
        supportFragmentManager.beginTransaction()
            .replace(R.id.article_frame_layout, fragment)
            .commit()
    }
}
