package com.hadiftech.demomashoor.network

import com.hadiftech.demomashoor.model.VideoResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/607183099fd99d1cb8e4")
    fun getVideoData(): Call<VideoResponse>
}