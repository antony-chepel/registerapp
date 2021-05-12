package com.tony_fire.signupapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import android.widget.Toast
import com.RegistrationActivity
import com.ThanksActivity
import com.retrofit.RetrofitInstance
import com.retrofit.UserInfo
import com.tony_fire.signupapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.app.Application
import android.graphics.Color
import com.appsflyer.AppsFlyerLib
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLibCore.LOG_TAG
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        videoPlayer()
        binding.button.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

    }

    private fun videoPlayer() {
        val videoPath = "android.resource://" + packageName + "/" + R.raw.video1
        val uri = Uri.parse(videoPath)
        binding.videoView.setVideoURI(uri)
        val mediaController = MediaController(this)
        binding.videoView.setMediaController(mediaController)
        mediaController.setAnchorView(binding.videoView)
        binding.videoView.setBackgroundColor(Color.TRANSPARENT)
        binding.videoView.start()

    }
}


