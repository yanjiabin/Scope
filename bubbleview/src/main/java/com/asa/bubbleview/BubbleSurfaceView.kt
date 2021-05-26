package com.asa.bubbleview

import android.content.Context
import android.graphics.Color
import android.graphics.DiscretePathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BubbleSurfaceView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        pathEffect=DiscretePathEffect(30f,5f)
    }

    private val colors = arrayOf(Color.YELLOW,Color.RED,Color.GREEN,Color.MAGENTA,Color.GRAY)

    private data class Bubble(val x :Float,val y :Float,val color:Int,var radiu :Float)

    private val bubbleList = mutableListOf<Bubble>()

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.x?:0f
        val y = event?.y?:0f
        val color = colors.random()
        bubbleList.add(Bubble(x,y,color,1f))
        if (bubbleList.size>30) bubbleList.removeAt(0)
        invalidate()
        return super.onTouchEvent(event)
    }


    init {
        CoroutineScope(Dispatchers.Default).launch {
            while (true){
                if (holder.surface.isValid) {
                    val canvas = holder.lockCanvas()
                    canvas.drawColor(Color.WHITE)
                    bubbleList.toList().filter { it.radiu<3000 }.forEach {
                        paint.color = it.color
                        canvas.drawCircle(it.x,it.y,it.radiu,paint)
                        it.radiu+= 10f
                    }
                    holder.unlockCanvasAndPost(canvas)
                }

            }
        }

    }



}