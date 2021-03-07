package com.hadiftech.demomashoor.view.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hadiftech.demomashoor.R
import com.hadiftech.demomashoor.model.Video

class VideoPlayerFragment(private val video: Video) : Fragment() {

    lateinit var videoView: VideoView
    lateinit var textVideoTitle: TextView
    lateinit var videoProgressBar: ProgressBar
    lateinit var seekBar: SeekBar
    lateinit var llHurray: LinearLayout
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null
    val SEEKBAR_REFRESH_DELAY = 10L

    lateinit var pageChangeListener: PageChangeListener
    var mediaPlayer: MediaPlayer? = null

    interface PageChangeListener {
        fun nextVideo(mp: MediaPlayer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageChangeListener = context as PageChangeListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val itemView: View = inflater.inflate(R.layout.item_video, container, false)

        //initializing views
        videoView = itemView.findViewById<View>(R.id.videoView) as VideoView
        textVideoTitle = itemView.findViewById<View>(R.id.textVideoTitle) as TextView
        videoProgressBar = itemView.findViewById<View>(R.id.videoProgressBar) as ProgressBar
        seekBar = itemView.findViewById<View>(R.id.seekBar) as SeekBar
        llHurray = itemView.findViewById<View>(R.id.llHurray) as LinearLayout

        //hiding thumb from seek bar
        seekBar.thumb.mutate().alpha = 0

        //setting fetched response values to views
        textVideoTitle.text = video.title
        videoView.setVideoPath(video.path)

        setListeners()

        return itemView
    }

    private fun setListeners() {

        videoView.setOnPreparedListener { mediaPlayer ->
            this.mediaPlayer = mediaPlayer

            //hiding progress bar as video is now prepared to start
            videoProgressBar.visibility = View.GONE
            mediaPlayer.start()

            //setting total seek bar duration to video duration
            seekBar.max = mediaPlayer.duration

            playCycle()
        }

        videoView.setOnCompletionListener { mediaPlayer ->
            pageChangeListener.nextVideo(mediaPlayer)
        }

        llHurray.setOnClickListener {
            showAlertDialog()
        }
    }

    //refreshing seek bar every 10 milliseconds
    private fun playCycle() {
        try {
            if (mediaPlayer != null && mediaPlayer!!.isPlaying) {
                seekBar.progress = mediaPlayer!!.currentPosition
                runnable = Runnable {
                    playCycle()
                }
                handler.postDelayed(runnable!!, SEEKBAR_REFRESH_DELAY)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showAlertDialog() {
        MaterialAlertDialogBuilder(
            requireActivity(),
            R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog
        )
            .setTitle(getString(R.string.hooray))
            .setMessage(R.string.you_are_awesome)
            .setCancelable(true)
            .setPositiveButton(R.string.i_am_awesome) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        //releasing media player to avoid illegal states
        mediaPlayer?.release()
        mediaPlayer = null
        if (runnable != null)
            handler.removeCallbacks(runnable!!)
    }
}