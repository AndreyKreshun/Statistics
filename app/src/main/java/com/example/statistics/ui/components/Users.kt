package com.example.statistics.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.statistics.ui.cards.UserCard
import com.example.statistics.functions.calculateVisitCount
import com.example.statistics.model.Statistic
import com.example.statistics.model.User

@Composable
fun Users(
    topVisitors: List<User>,
    statistics: List<Statistic>
) {
    Column {
        Text(
            text = "Чаще всех посещают Ваш профиль",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (topVisitors.isEmpty()) {
            Text("Нет данных о посетителях", color = Color.Gray)
        } else {
            val sortedVisitors = remember(topVisitors, statistics) {
                topVisitors.sortedByDescending { user ->
                    calculateVisitCount(user.id, statistics)
                }
            }

            Column {
                sortedVisitors.forEachIndexed { index, user ->
                    UserCard(
                        user = user,
                        position = index + 1,
                        visitCount = calculateVisitCount(user.id, statistics)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}
