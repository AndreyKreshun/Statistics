package com.example.statistics.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun LineChart(labels: List<String>, values: List<Int>) {
    val maxValue = (values.maxOrNull() ?: 1).toFloat()

    Column(modifier = Modifier.fillMaxWidth()) {
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 8.dp)
        ) {
            val spacing = size.width / (values.size - 1).coerceAtLeast(1)
            val points = values.mapIndexed { index, value ->
                Offset(
                    x = index * spacing,
                    y = size.height - (value / maxValue) * size.height
                )
            }


            for (i in 0 until points.size - 1) {
                drawLine(
                    color = Color.Red,
                    start = points[i],
                    end = points[i + 1],
                    strokeWidth = 4f,
                    cap = StrokeCap.Round
                )
            }


            points.forEach {
                drawCircle(Color.Red, radius = 6f, center = it)
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            labels.forEach { label ->
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }
    }
}