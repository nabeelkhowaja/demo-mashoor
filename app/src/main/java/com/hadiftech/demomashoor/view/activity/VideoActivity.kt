package com.hadiftech.demomashoor.view.activity

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.hadiftech.demomashoor.*
import com.hadiftech.demomashoor.adapter.VideoAdapter
import com.hadiftech.demomashoor.model.VideoResponse
import com.hadiftech.demomashoor.view.CustomViewPager
import com.hadiftech.demomashoor.view.fragment.VideoPlayerFragment

class VideoActivity : AppCompatActivity(), VideoPlayerFragment.PageChangeListener {

    //CustomViewPager is used to load fragment (video player) only when it is visible
    lateinit var videoViewPager: CustomViewPager
    lateinit var videos: VideoResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        videoViewPager = findViewById(R.id.viewPager)

        //getting intent extras (video metadata)
        videos = Gson().fromJson(
            intent.getStringExtra(getString(R.string.key_video_metadata)),
            VideoResponse::class.java
        )

        //passing video metadata to adapter
        videoViewPager.adapter = VideoAdapter(supportFragmentManager, videos)
    }

    //runs when video is completed
    override fun nextVideo(mp: MediaPlayer) {
        if (videoViewPager.currentItem == (videos.videos.size - 1))//closing video player activity if last video
            finish()
        else //moving to next video if not last
            videoViewPager.setCurrentItem(videoViewPager.currentItem + 1, true)
    }
}