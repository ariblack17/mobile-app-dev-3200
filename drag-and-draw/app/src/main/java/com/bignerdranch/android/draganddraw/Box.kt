package com.bignerdranch.android.draganddraw

import android.graphics.PointF
import kotlin.math.abs

data class Box(val start: PointF) {

    var end: PointF = start

    val left: Float
        get() = Math.min(start.x, end.x)

    val right: Float
        get() = Math.max(start.x, end.x)

    val top: Float
        get() = Math.min(start.y, end.y)

    val bottom: Float
        get() = Math.max(start.y, end.y)

    // new code - track height and width for box //

    val height: Float
        get() = abs(top - bottom)

    val width: Float
        get() = abs(left - right)

}
