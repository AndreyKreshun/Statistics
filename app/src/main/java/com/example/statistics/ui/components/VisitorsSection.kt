package com.example.statistics.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.statistics.ui.cards.VisitorsCard
import com.example.statistics.model.Statistic
import com.example.statistics.ui.button.PeriodButton

@Composable
fun VisitorsSection(visitorCount: Int, statistics: List<Statistic>) {
    var selectedPeriod by remember { mutableStateOf("days") }

    Column {
        Text(
            text = "Посетители",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        VisitorsCard(
            visitorCount = visitorCount,
            growth = true,
            message = "Количество посетителей в этом месяце выросло"
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            PeriodButton(
                text = "По дням",
                isSelected = selectedPeriod == "days",
                onClick = { selectedPeriod = "days" }
            )
            PeriodButton(
                text = "По неделям",
                isSelected = selectedPeriod == "weeks",
                onClick = { selectedPeriod = "weeks" }
            )
            PeriodButton(
                text = "По месяцам",
                isSelected = selectedPeriod == "months",
                onClick = { selectedPeriod = "months" }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ChartByPeriod(statistics = statistics, period = selectedPeriod)
    }
}