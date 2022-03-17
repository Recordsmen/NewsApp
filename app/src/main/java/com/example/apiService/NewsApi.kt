package com.example.apiService

import com.example.utils.Constants
import com.example.utils.Constants.BASE_URL

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.http.GET


object NewsApi {

    private val retrofit = Retrofit.Builder()
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

    interface NewsApiServices {
        @GET(Constants.API_KEY)
        suspend fun getNewsFromApi(): ResponseBody
    }

    val retrofitService: NewsApiServices by lazy {
        retrofit.create(NewsApiServices::class.java)
    }

}
