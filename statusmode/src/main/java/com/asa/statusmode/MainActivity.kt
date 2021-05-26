package com.asa.statusmode

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {


    private var imageView:ImageView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById<ImageView>(R.id.imageView)
        val translationX = ObjectAnimator.ofFloat(imageView!!, View.TRANSLATION_X, 0f, 300f)
        translationX.duration = 2000
        translationX.repeatCount = ValueAnimator.INFINITE
        translationX.repeatMode = ValueAnimator.REVERSE
        translationX.interpolator = AccelerateInterpolator()
        translationX.start()

    }

}