package com.hadiftech.demomashoor.view.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textview.MaterialTextView
import com.google.gson.Gson
import com.hadiftech.demomashoor.network.ApiService
import com.hadiftech.demomashoor.R
import com.hadiftech.demomashoor.network.RestClient
import com.hadiftech.demomashoor.model.VideoResponse
import com.hadiftech.demomashoor.viewmodel.HomeActivityViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    lateinit var builder: AlertDialog.Builder
    lateinit var dialog: Dialog
    lateinit var homeActivityViewModel: HomeActivityViewModel
    val DELAY_LOGO = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //setting up view model for a network call
        homeActivityViewModel = ViewModelProvider(this).get(HomeActivityViewModel::class.java)

        val tvStart = findViewById<MaterialTextView>(R.id.tvStart)

        //show start button after 2 seconds with animation
        Handler(Looper.getMainLooper()).postDelayed({
            tvStart.visibility = View.VISIBLE
        }, DELAY_LOGO)

        tvStart.setOnClickListener {
            if (isInternetAvailable()) {
                showProgressAlertDialog()
                //get video metadata from server containing title and URL of a video
                homeActivityViewModel.getVideoMetadata()!!.observe(this, { videoResponse ->
                    dismissDialog()
                    if (videoResponse != null) {
                        //passing metadata to VideoActivity class
                        val gson = Gson()
                        val openVideoActivity = Intent(this@HomeActivity, VideoActivity::class.java)
                        openVideoActivity.putExtra(
                            getString(R.string.key_video_metadata),
                            gson.toJson(videoResponse)
                        )
                        startActivity(openVideoActivity)
                    } else
                        showToast(getString(R.string.something_went_wrong))
                })
            } else
                showToast(getString(R.string.internet_not_available))
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isAvailable && connectivityManager.activeNetworkInfo!!.isConnected
    }

    private fun dismissDialog() {
        if (dialog.isShowing)
            dialog.dismiss()
    }

    private fun showProgressAlertDialog() {
        builder = AlertDialog.Builder(this@HomeActivity)
        val view = View.inflate(this, R.layout.dialog_progress, null)
        view.findViewById<TextView>(R.id.tvProgress).text =
            getString(R.string.fetching_video_metadata)
        builder.setView(view.rootView)
        dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        //setting dialog width to be 70 percent of screen width
        dialog.window?.setLayout(
            (displayMetrics.widthPixels * 0.7f).toInt(),
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    private fun showToast(msg: String) {
        Toast.makeText(
            this@HomeActivity,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }
}