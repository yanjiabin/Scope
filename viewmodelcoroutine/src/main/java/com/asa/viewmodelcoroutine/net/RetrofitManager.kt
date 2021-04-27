package com.asa.viewmodelcoroutine.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {

    private val client :OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }


     val retrofitManager:Retrofit by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl("https://www.wanandroid.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val serverManager :ServiceAPI by lazy { retrofitManager.create(ServiceAPI::class.java) }




}