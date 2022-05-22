package com.visa.timesreader.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visa.timesreader.models.NewsItemModel
import com.visa.timesreader.repository.Repository
import com.visa.timesreader.utils.Category
import kotlinx.coroutines.launch

class NewsItemViewModel(private val repository: Repository) : ViewModel() {

    var selectedCategory: Category = Category.Home
    var data: MutableLiveData<NewsItemModel> = MutableLiveData()

    fun getNews() {
        viewModelScope.launch {
            data.value = repository.getNews(selectedCategory.route)
        }
    }
}