package com.visa.timesreader

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import com.visa.timesreader.adapters.NewsAdapter
import com.visa.timesreader.databinding.ActivityMainBinding
import com.visa.timesreader.repository.Repository
import com.visa.timesreader.utils.Category
import com.visa.timesreader.views.NewsItemViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var activityBinding: ActivityMainBinding
    private lateinit var newsItemViewModel: NewsItemViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = ActivityMainBinding.inflate(layoutInflater)
        showLoadingIndicator()

        /**
         * Initialize Retrofit-instance and fetch the Homepage news.
         */
        val repository = Repository()
        newsItemViewModel = NewsItemViewModel(repository)
        newsItemViewModel.getNews()
        populateCategoryMenu(this, newsItemViewModel)

        /**
         * Wait for data from the server and add adapter to RecyclerView when LiveData is ready.
         */
        newsItemViewModel.data.observe(this) { response ->
            try {
                newsAdapter = NewsAdapter(response.results)
                activityBinding.newsList.adapter = newsAdapter
                hideLoadingIndicator()
            } catch (e: Exception) {
                Log.e("Data observe exception", e.message!!)
            }
        }

        val view = activityBinding.root
        setContentView(view)
    }

    /**
     * Show loading indicator and hide list of news
     */
    private fun showLoadingIndicator() {
        activityBinding.loadingIndicator.visibility = View.VISIBLE
        activityBinding.newsList.visibility = View.INVISIBLE
    }

    /**
     * Hide loading indicator and show list of news
     */
    private fun hideLoadingIndicator() {
        activityBinding.loadingIndicator.visibility = View.INVISIBLE
        activityBinding.newsList.visibility = View.VISIBLE
    }

    /**
     * Create list of Categories using the sealed class from /utils.
     * Iterate list of categories and add a button for each one inside the LinearLayout.
     */
    private fun populateCategoryMenu(context: Context, viewModel: NewsItemViewModel) {
        val categoryMenuList = activityBinding.categoryMenuList
        val categories = listOf(
            Category.Home,
            Category.Arts,
            Category.Business,
            Category.Fashion,
            Category.Health,
            Category.World
        )
        val group = RadioGroup(context).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        /**
         * Layout Parameters for each CategoryButton
         * Needed to set margins dynamically
         */
        val defaultCategory = Category.Home
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(24, 12, 24, 12)
        }
        val themeWrapper = ContextThemeWrapper(context, R.style.CategoryButton)
        categories.forEach { category ->
            val buttonContainer = AppCompatRadioButton(themeWrapper).apply {
                background =
                    ContextCompat.getDrawable(context, R.drawable.radio_button_custom)
                buttonDrawable = null
                text = category.title
                layoutParams = params
                id = View.generateViewId()
            }

            if (category == defaultCategory) {
                buttonContainer.isChecked = true
            }
            group.addView(buttonContainer)
            buttonContainer.setOnClickListener {
                showLoadingIndicator()
                viewModel.selectedCategory = category
                viewModel.getNews()
            }
        }
        categoryMenuList.addView(group)
    }
}