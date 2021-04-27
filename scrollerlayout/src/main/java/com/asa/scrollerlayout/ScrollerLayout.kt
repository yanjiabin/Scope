package com.asa.scrollerlayout

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager

class ScrollerLayout:ViewGroup {


    private var childWidth = 0
    private var childHeight = 0
    private var screenWidth = 0
    private var space = 0

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        space = getContext().resources.getDimensionPixelOffset(R.dimen.dp_4)
        val windowManager = getContext().applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        screenWidth = windowManager.defaultDisplay.width
        childWidth = screenWidth/3 - space * 3
        childHeight = childWidth
    }

    fun initChildViews(){
        for(i in 0 until 12){
            val itemView = ItemView(context)
            itemView.setTextContent(i)
            addView(itemView)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth,widthMode),MeasureSpec.makeMeasureSpec(childHeight,heightMode))
        Log.e(">>>net","width:${measuredWidth}")
        Log.e(">>>net","height:${measuredHeight}")
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {



    }


}