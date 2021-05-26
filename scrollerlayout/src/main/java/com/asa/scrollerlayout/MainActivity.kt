package com.asa.scrollerlayout

import android.graphics.ImageDecoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val scrollerLayout = ScrollerLayout(this)
        setContentView(R.layout.activity_main)
        val scrollerLayout = findViewById<ScrollerLayout>(R.id.scrollerLayout)
        scrollerLayout.initChildViews()
    }
}