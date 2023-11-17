package com.bignerdranch.android.draganddraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

private const val TAG = "BoxDrawingView"

class BoxDrawingView(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var currentBox: Box? = null
    private val boxes = mutableListOf<Box>()
    private val boxPaint = Paint().apply {
        color = 0x22ff0000.toInt()
    }
    private val backgroundPaint = Paint().apply {
        color = 0xfff8efe0.toInt()
    }
    private var numShapes: Int = 0          // part 4 //
    private var drawing: Boolean = false    // part 3 //

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        var action = ""
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
                // Reset drawing state
                // code for part 4 //
                if (numShapes >= 3) {
                    currentBox = null
                } else {
                    currentBox = Box(current).also {
                        boxes.add(it)
                    }
                    // part 3 //
                    drawing = true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
                updateCurrentBox(current)
            }
            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
                updateCurrentBox(current)
                // code for part 3 //
                currentBox = null
                drawing = false
                numShapes += 1
            }
            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEL"
                currentBox = null
            }
        }

        Log.i(TAG, "$action at x=${current.x}, y=${current.y}")

        return true
    }

    override fun onDraw(canvas: Canvas) {
        // Fill the background
        canvas.drawPaint(backgroundPaint)

        boxes.forEach { box ->
            // original code //
//            canvas.drawRect(box.left, box.top, box.right, box.bottom, boxPaint)

            // code for part 2 //
//            canvas.drawRect(box.left, box.top, box.left+box.width, box.top+box.width, boxPaint)       // only draw squares

            // code for part 3 //
            if(drawing && currentBox == box) {
                // draw rectangle while dragging
            canvas.drawRect(box.left, box.top, box.right, box.bottom, boxPaint) }
            else {
                // draw square if width > height
                if(box.width > box.height)  {canvas.drawRect(box.left, box.top, box.left+box.width, box.top+box.width, boxPaint)}
                // draw circle otherwise
//                else {canvas.drawCircle((box.left + box.right)/2, (box.top + box.bottom)/2, box.width, boxPaint)}
                else {canvas.drawOval(box.left, box.top, box.left+box.width, box.top+box.width, boxPaint)}
            }
        }
    }

    private fun updateCurrentBox(current: PointF) {
        currentBox?.let {
            it.end = current
            invalidate()
        }
    }
}
