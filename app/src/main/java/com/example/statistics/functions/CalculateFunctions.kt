package com.example.statistics.functions

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.statistics.model.Statistic
import com.example.statistics.model.User
import java.time.LocalDate

internal fun calculateVisitCount(userId: Int, statistics: List<Statistic>): Int {
    return statistics
        .filter { it.user_id == userId && it.type == "view" }
        .sumOf { it.dates.size }
}

@RequiresApi(Build.VERSION_CODES.O)
fun filterUsersByStatisticPeriod(
    users: List<User>,
    statistics: List<Statistic>,
    period: String
): List<User> {
    val now = LocalDate.now()

    return users.filter { user ->
        statistics
            .filter { it.user_id == user.id && it.type == "view" }
            .flatMap { it.dates }
            .map(::parseDate)
            .any { visitDate ->
                when (period) {
                    "today" -> visitDate.isEqual(now)
                    "week" -> visitDate.isAfter(now.minusDays(7)) || visitDate.isEqual(now)
                    "month" -> visitDate.isAfter(now.minusDays(30)) || visitDate.isEqual(now)
                    "all time" -> true
                    else -> true
                }
            }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun parseDate(intDate: Int): LocalDate {
    val str = intDate.toString().padStart(8, '0')
    val day = str.substring(0, 2).toInt()
    val month = str.substring(2, 4).toInt()
    val year = str.substring(4).toInt()
    return LocalDate.of(year, month, day)
}