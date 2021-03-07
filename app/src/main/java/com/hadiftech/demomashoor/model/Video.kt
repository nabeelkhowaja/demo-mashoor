package com.hadiftech.demomashoor.model

import com.google.gson.annotations.SerializedName

data class Video(

    @SerializedName("title")
    var title: String,

    @SerializedName("path")
    var path: String
)