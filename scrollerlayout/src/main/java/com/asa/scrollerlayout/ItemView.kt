package com.asa.scrollerlayout

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.TextView

class ItemView:FrameLayout {

    private var textView:TextView?=null

    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        val layoutInflater = LayoutInflater.from(getContext())
        layoutInflater.inflate(R.layout.item_layout,this,true)

        textView = findViewById<TextView>(R.id.tv)
    }


    fun setTextContent(content:Int){
        textView?.text = content.toString()
    }
}