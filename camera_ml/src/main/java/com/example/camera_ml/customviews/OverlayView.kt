package com.example.camera_ml.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Rect
import android.view.View

class OverlayView(
     context: Context
): View(context) {
    private var paint = Paint().apply {
        style = Paint.Style.STROKE  // This makes it draw only the outline
        strokeWidth = 5f
        textSize = 30f   // Set the width of the outline
        color = Color.WHITE        // Set the color of the outline
        isAntiAlias = true
        strokeJoin = Paint.Join.ROUND  // Rounds the corners where lines meet
        strokeCap = Paint.Cap.ROUND    // Rounds the ends of lines
        pathEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
    }
    private var rectToDraw: List<Pair<Rect?, String>> = emptyList()


    fun setRectangles(rects: List<Pair<Rect?, String>>){
        rectToDraw = rects
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rectToDraw.forEach(){
            if(it.first != null){

                canvas.drawRect(it.first!!, paint)
                canvas.drawText(it.second, it.first!!.left.toFloat(), it.first!!.top.toFloat(), paint)
            }
        }

    }
}