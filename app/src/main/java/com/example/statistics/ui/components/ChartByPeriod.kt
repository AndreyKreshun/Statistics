package com.example.statistics.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.statistics.model.Statistic
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun ChartByPeriod(statistics: List<Statistic>, period: String) {
    val dateFormatter = SimpleDateFormat("ddMMyyyy", Locale.getDefault())
    val calendar = Calendar.getInstance()

    val allDates = statistics
        .filter { it.type == "view" }
        .flatMap { it.dates }
        .mapNotNull {
            try {
                dateFormatter.parse(it.toString())
            } catch (e: Exception) {
                null
            }
        }

    val grouped: Map<String, Int> = when (period) {
        "days" -> {
            allDates.groupingBy {
                SimpleDateFormat("dd.MM", Locale.getDefault()).format(it)
            }.eachCount()
        }
        "weeks" -> {
            allDates.groupingBy {
                val week = calendar.apply { time = it }.get(Calendar.WEEK_OF_YEAR)
                "Неделя $week"
            }.eachCount()
        }
        "months" -> {
            allDates.groupingBy {
                val month = calendar.apply { time = it }.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("ru")) ?: ""
                month.replaceFirstChar { it.uppercase() }
            }.eachCount()
        }
        else -> emptyMap()
    }

    val sortedData = grouped.toSortedMap(compareBy {
        when (period) {
            "days" -> SimpleDateFormat("dd.MM", Locale.getDefault()).parse(it)?.time ?: 0
            "weeks" -> it.filter { ch -> ch.isDigit() }.toIntOrNull() ?: 0
            "months" -> listOf(
                "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
            ).indexOf(it)
            else -> 0
        }
    })

    if (sortedData.isEmpty()) {
        Text("Нет данных для отображения", color = Color.Gray)
    } else {
        LineChart(
            labels = sortedData.keys.toList(),
            values = sortedData.values.toList()
        )
    }
}