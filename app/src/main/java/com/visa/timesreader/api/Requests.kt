package com.visa.timesreader.api

import com.visa.timesreader.models.NewsItemModel
import retrofit2.http.GET
import com.visa.timesreader.utils.Constants.Companion.verySecretive
import retrofit2.http.Path

interface Requests {

    @GET("{category}.json${verySecretive}")
    suspend fun getNews(@Path("category") category: String) : NewsItemModel
}