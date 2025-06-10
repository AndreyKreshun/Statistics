package com.example.statistics.ui.components

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.statistics.functions.filterUsersByStatisticPeriod
import com.example.statistics.model.Statistic
import com.example.statistics.model.User
import com.example.statistics.ui.button.PeriodButton
import com.example.statistics.ui.theme.FemaleColor
import com.example.statistics.ui.theme.MaleColor

@Composable
fun GenderAgeSection(users: List<User>, statistics: List<Statistic>) {
    var selectedPeriod by remember { mutableStateOf("today") }
    val filteredUsers = remember(users, statistics, selectedPeriod) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            filterUsersByStatisticPeriod(users, statistics, selectedPeriod)
        } else {
            users
        }
    }
    val totalUsers = filteredUsers.size
    val maleCount = filteredUsers.count { it.sex == "M" }
    val femaleCount = filteredUsers.count { it.sex == "W" }

    val malePercentage = if (totalUsers > 0) maleCount * 100f / totalUsers else 0f
    val femalePercentage = if (totalUsers > 0) femaleCount * 100f / totalUsers else 0f

    Column {
        Text(
            text = "Пол и возраст",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            PeriodButton(
                text = "Сегодня",
                isSelected = selectedPeriod == "today",
                onClick = { selectedPeriod = "today" }
            )
            PeriodButton(
                text = "Неделя",
                isSelected = selectedPeriod == "week",
                onClick = { selectedPeriod = "week" }
            )
            PeriodButton(
                text = "Месяц",
                isSelected = selectedPeriod == "month",
                onClick = { selectedPeriod = "month" }
            )
            PeriodButton(
                text = "Все время",
                isSelected = selectedPeriod == "all time",
                onClick = { selectedPeriod = "all time" }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Круговая диаграмма
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(180.dp),
                contentAlignment = Alignment.Center
            ) {
                RingChart(
                    malePercentage = malePercentage,
                    femalePercentage = femalePercentage,
                    maleColor = MaleColor,
                    femaleColor = FemaleColor
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            GenderLegendItem(color = MaleColor, label = "Мужчины", percentage = malePercentage)
            Spacer(modifier = Modifier.width(16.dp))
            GenderLegendItem(color = FemaleColor, label = "Женщины", percentage = femalePercentage)
        }

        val ageGroups: List<Pair<String, IntRange>> = listOf(
            "<18" to (0..18),
            "18–21" to (18..21),
            "22–25" to (22..25),
            "26–30" to (26..30),
            "31–35" to (31..35),
            "36–40" to (36..40),
            "40–50" to (40..50),
            ">50" to (51..150) // верхняя граница условная
        )


        ageGroups.forEach { (label: String, range: IntRange) ->
            val maleCount = users.count { it.sex == "M" && it.age in range }
            val femaleCount = users.count { it.sex == "W" && it.age in range }

            val malePercentage = if (totalUsers > 0) maleCount * 100f / totalUsers else 0f
            val femalePercentage = if (totalUsers > 0) femaleCount * 100f / totalUsers else 0f

            AgeGroupItem(
                ageGroup = label,
                malePercentage = malePercentage,
                femalePercentage = femalePercentage
            )
        }

    }
}