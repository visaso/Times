package com.visa.timesreader.repository

import com.visa.timesreader.api.RetrofitImpl
import com.visa.timesreader.models.NewsItemModel

class Repository {

    suspend fun getNews(category: String): NewsItemModel {
        return RetrofitImpl.api.getNews(category)
    }
}