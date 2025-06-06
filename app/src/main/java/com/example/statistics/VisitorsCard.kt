package com.example.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VisitorsCard(
    visitorCount: Int,
    growth: Boolean,
    message: String
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (growth) {
                Image(
                    painter = painterResource(id = R.drawable.ic_graph_up),
                    contentDescription = "График роста",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 12.dp)
                )
            }
            else{
                Image(
                    painter = painterResource(id = R.drawable.ic_down_graph),
                    contentDescription = "График падения",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 12.dp)
                )
            }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = visitorCount.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    if (growth) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_up_arrow),
                            contentDescription = "Рост",
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(18.dp)
                        )
                    }
                    else{
                        Image(
                            painter = painterResource(id = R.drawable.ic_down_arrow),
                            contentDescription = "Падение",
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(18.dp)
                        )
                    }
                }
                Text(
                    text = message,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}
