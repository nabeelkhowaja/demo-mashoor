package com.hadiftech.demomashoor.repository

import android.util.Log
import com.hadiftech.demomashoor.network.SingleLiveEvent
import com.hadiftech.demomashoor.model.VideoResponse
import com.hadiftech.demomashoor.network.ApiService
import com.hadiftech.demomashoor.network.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object VideoRepository {

    val videoResponse =
        SingleLiveEvent<VideoResponse>()

    //getting video data
    fun getVideoDataApiCall(): SingleLiveEvent<VideoResponse> {
        val call: Call<VideoResponse> =
            RestClient.getRetrofitInstance()!!.create(ApiService::class.java).getVideoData()
        call.enqueue(object : Callback<VideoResponse> {

            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                //posting response
                videoResponse.value = response.body()
            }

            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }
        })
        return videoResponse
    }
}