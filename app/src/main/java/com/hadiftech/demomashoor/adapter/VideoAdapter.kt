package com.hadiftech.demomashoor.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.hadiftech.demomashoor.model.VideoResponse
import com.hadiftech.demomashoor.view.fragment.VideoPlayerFragment

class VideoAdapter(fm: FragmentManager, private val videoResponse: VideoResponse) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = videoResponse.videos.size

    override fun getItem(position: Int): Fragment =
        VideoPlayerFragment(videoResponse.videos[position])
}