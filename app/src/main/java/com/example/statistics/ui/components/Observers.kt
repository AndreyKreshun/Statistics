package com.example.statistics.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.statistics.ui.cards.VisitorsCard

@Composable
fun Observers(){
    Column {
        Text(
            text = "Наблюдатели",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        VisitorsCard(
            visitorCount = 1356,
            growth = true,
            message = "Новые наблюдатели в этом месяце"
        )
        VisitorsCard(
            visitorCount = 10,
            growth = false,
            message = "Пользователи перестали за Вами наблюдать"
        )
    }
}