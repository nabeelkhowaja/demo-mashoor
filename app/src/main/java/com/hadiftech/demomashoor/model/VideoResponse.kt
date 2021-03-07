package com.hadiftech.demomashoor.model

import com.google.gson.annotations.SerializedName

data class VideoResponse(

    @SerializedName("videos")
    var videos: List<Video>
)