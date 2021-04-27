package com.asa.viewmodelcoroutine.net

import com.asa.viewmodelcoroutine.bean.Banner
import retrofit2.http.GET

interface ServiceAPI {
    @GET("/banner/json")
    suspend fun getBanner(): Banner
}