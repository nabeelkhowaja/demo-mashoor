package com.hadiftech.demomashoor.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hadiftech.demomashoor.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.FullScreen);
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
    }
}