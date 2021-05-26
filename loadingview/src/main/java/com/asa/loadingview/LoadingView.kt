package com.asa.loadingview

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.View
import androidx.core.content.ContextCompat
import kotlin.math.max

class LoadingView:View {

    companion object{
        val PLAY_STATE_PLAYING = 1
    }

    private val paint = Paint()
    private var bgColor = 0
    private var columnarTopColor = 0
    private var columnarBottomColor = 0
    private var diameter = 0

    private var dotType = 0
    private var dotCount = 0

    private var columnarSpan = 5
    private var columnarWidth = 0
    private var columnarDefaultHeight = 40f
    private var columnarTop = 0f
    private var columnarBottom = 0f
    private val rect = RectF()


    private var columnar1AnimTop = columnarDefaultHeight
    private var columnar2AnimTop = columnarDefaultHeight
    private var columnar3AnimTop = columnarDefaultHeight
    private val columnarAnimSet = AnimatorSet()

    var duration = 800L
    var play_state = PLAY_STATE_PLAYING


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        bgColor = ContextCompat.getColor(context, R.color.color_90B2FF)
        columnarTopColor = ContextCompat.getColor(context, R.color.white)
        columnarBottomColor = ContextCompat.getColor(context, R.color.color_D3E0FF)
        columnarWidth = context.resources.getDimensionPixelOffset(R.dimen.dp_10)
        columnarSpan = context.resources.getDimensionPixelOffset(R.dimen.dp_3)
        paint.isAntiAlias = true
        paint.isDither = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        diameter = max(width, height)
        setMeasuredDimension(diameter, diameter)
        columnarTop = (diameter * 0.24f)
        columnarBottom = (diameter * 0.76f)
        initColumnarAnim()
    }

    private fun initColumnarAnim(){
        val minHeight = columnarBottom * 0.8f
        val columnar1Anim = ValueAnimator.ofFloat(minHeight,columnarTop)
        columnar1Anim.repeatMode = ValueAnimator.REVERSE
        columnar1Anim.repeatCount = ValueAnimator.INFINITE

        val columnar2Anim = columnar1Anim.clone()
        val columnar3Anim = columnar1Anim.clone()

        columnar3Anim.addUpdateListener {
            columnar1AnimTop = columnar1Anim.animatedValue as Float
            columnar2AnimTop = columnar2Anim.animatedValue as Float
            columnar3AnimTop = columnar3Anim.animatedValue as Float
            invalidate()
        }
        columnar2Anim.startDelay = duration / 2
        columnar3Anim.startDelay = duration

        columnarAnimSet.playTogether(columnar1Anim,columnar2Anim,columnar3Anim)
        columnarAnimSet.duration = duration
        columnarAnimSet.start()
    }



    override fun onDraw(canvas: Canvas) {
        drawBg(canvas)
        if (play_state == PLAY_STATE_PLAYING){
            drawColumnar(canvas)
        }
    }
    
    private fun drawColumnar(canvas: Canvas) {
        paint.color = columnarTopColor
        var left = diameter / 2 - columnarWidth/2 - columnarWidth - columnarSpan
        var top = columnarTop / 2f
        var right = left + columnarWidth
        val bottom = columnarBottom
        for (i in 1..3){
            top = getTop(i)
            rect.set(left.toFloat(), top, right.toFloat(), bottom.toFloat())
            val shader = getLinearGradient(top)
            paint.shader = shader
            canvas.drawRoundRect(rect, 4f, 4f, paint)
            left += (columnarWidth + columnarSpan)
            right = left + columnarWidth
        }
    }

    private val shaderList = SparseArray<LinearGradient>()

    private fun getLinearGradient(top:Float):LinearGradient{
        var shader = shaderList.get(top.toInt())
        if (shader == null){
            shader = LinearGradient(0f, top, 0f, bottom.toFloat(), columnarTopColor,columnarBottomColor, Shader.TileMode.CLAMP)
            shaderList.put(top.toInt(),shader)
        }
        return shader
    }

    private fun getTop(index: Int): Float {
        return when(index) {
            1-> columnar1AnimTop
            2-> columnar2AnimTop
            3-> columnar3AnimTop
            else -> columnarDefaultHeight
        }
    }



    private fun drawBg(canvas: Canvas) {
        paint.color = bgColor
        paint.shader = null
        canvas.drawCircle(diameter / 2f, diameter / 2f, diameter / 2f, paint)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (play_state == PLAY_STATE_PLAYING){

            columnarAnimSet.resume()
            columnarAnimSet.start()
        }else{
            columnarAnimSet.pause()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        columnarAnimSet.pause()
    }




}