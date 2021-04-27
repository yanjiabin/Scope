package com.asa.viewmodelcoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import com.asa.viewmodelcoroutine.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private var tv:TextView?=null
    private val mainViewModel by lazy { MainViewModel() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.tv)
        mainViewModel.bannerObservable.observe(this, Observer {
            val content: List<String> = it.map {
                it.title
            }
            tv?.text = content.toTypedArray().contentToString()
        })
        mainViewModel.getBanner()
    }

}