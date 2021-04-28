package com.asa.scrollerlayout

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.Scroller
import androidx.core.os.ConfigurationCompat
import javax.security.auth.login.LoginException
import kotlin.math.abs

class ScrollerLayout:ViewGroup {


    private var childWidth = 0
    private var childHeight = 0
    private var screenWidth = 0
    private var space = 0
    private var scroller:Scroller?=null
    private var scaledPagingTouchSlop = 0
    private var downX = 0
    private var moveX  = 0
    private var lastX = 0

    constructor(context: Context?) : this(context,null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        scroller = Scroller(getContext())

        val viewConfiguration = ViewConfiguration.get(getContext())
        scaledPagingTouchSlop = viewConfiguration.scaledPagingTouchSlop/4
        Log.e(">>>net","scaledPagingTouchSlop:${scaledPagingTouchSlop}")
        space = getContext().resources.getDimensionPixelOffset(R.dimen.dp_4)
        val windowManager = getContext().applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        screenWidth = windowManager.defaultDisplay.width
        childWidth = screenWidth/3
        childHeight = childWidth
    }

    fun initChildViews(){
        for(i in 0 until 12){
            val itemView = ItemView(context)
            itemView.setTextContent(i)
            addView(itemView,LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT))
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth,MeasureSpec.EXACTLY),MeasureSpec.makeMeasureSpec(childHeight,MeasureSpec.EXACTLY))
    }

    override fun onLayout(change: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        var childTop = 0
        var childRight = 0
        var childBottom = 0
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            val childHeight: Int = childView.measuredHeight
            val childRowPosition = i % 2  //行数
            val childColumnPosition = i / 2  //列数
            childLeft = if (i < 2) {
                childColumnPosition * childWidth + space/2
            } else {
                childColumnPosition * childWidth + space/2
            }
            childRight = childLeft + childWidth - space/2
            childTop = if (i % 2 == 0) {
                childRowPosition * childHeight+ space/2
            } else {
                childRowPosition * childHeight+ space
            }
            childBottom = childTop + childHeight
            childView.layout(childLeft, childTop, childRight, childBottom)
        }
        Log.e(">>>net","borderRight:${getChildAt(childCount - 1).right}");
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.e(">>>net","MotionEvent.ACTION_DOWN")
                downX = ev.rawX.toInt()
                lastX = downX
            }
            MotionEvent.ACTION_MOVE->{
                Log.e(">>>net","MotionEvent.ACTION_MOVE")
                moveX = ev.rawX.toInt()
                lastX = moveX
                val diff = abs(moveX - downX)
                Log.e(">>>net","diff:$diff")
                if (diff > scaledPagingTouchSlop){
                    return true
                }
            }
            else -> {
            }
        }
        return super.onInterceptTouchEvent(ev)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE->{
                moveX = event.rawX.toInt()
                val scrollX = lastX - moveX
                if (getScrollX() + scrollX < 0){
                    scrollTo(0,0)
                    return true
                } else if (getScrollX() + scrollX > getChildAt(childCount-1).right){
                    scrollTo(getChildAt(childCount-1).right,0)
                    return true
                }
                scrollBy(scrollX,0)
                lastX = moveX
            }
            MotionEvent.ACTION_UP->{
                val targetIndex = (scrollX + width /2)/width
                val dx = targetIndex * width - scrollX
                scroller?.startScroll(scrollX,0,dx,0)
                invalidate()
            }
            else -> {
            }
        }
        return super.onTouchEvent(event)

    }


    override fun computeScroll() {
        if (scroller!!.computeScrollOffset()){
            scrollTo(scroller!!.currX,scroller!!.currY)
            invalidate()
        }
    }


}