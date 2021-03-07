package com.hadiftech.demomashoor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hadiftech.demomashoor.network.SingleLiveEvent
import com.hadiftech.demomashoor.model.VideoResponse
import com.hadiftech.demomashoor.repository.VideoRepository

class HomeActivityViewModel : ViewModel(){

    private var videoResponseLiveData : SingleLiveEvent<VideoResponse>? = null

    fun getVideoMetadata(): LiveData<VideoResponse>?{
        videoResponseLiveData = VideoRepository.getVideoDataApiCall()
        return videoResponseLiveData
    }
}