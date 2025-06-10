package com.example.statistics.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun RingChart(
    malePercentage: Float,
    femalePercentage: Float,
    maleColor: Color,
    femaleColor: Color
) {
    val strokeWidth = 16f
    val sweepMale = malePercentage * 3.6f
    val sweepFemale = femalePercentage * 3.6f

    Canvas(modifier = Modifier.size(150.dp)) {
        val diameter = size.minDimension
        val radius = diameter / 2
        val center = Offset(radius, radius)
        val rect = Rect(center - Offset(radius - strokeWidth / 2, radius - strokeWidth / 2),
            center + Offset(radius - strokeWidth / 2, radius - strokeWidth / 2))

        drawArc(
            color = maleColor,
            startAngle = -90f,
            sweepAngle = sweepMale,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            size = Size(diameter, diameter),
            topLeft = Offset(0f, 0f)
        )

        drawArc(
            color = femaleColor,
            startAngle = -90f + sweepMale,
            sweepAngle = sweepFemale,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            size = Size(diameter, diameter),
            topLeft = Offset(0f, 0f)
        )
    }
}
