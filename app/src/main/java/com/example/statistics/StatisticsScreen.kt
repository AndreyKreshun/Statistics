package com.example.statistics

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.statistics.model.User
import com.example.statistics.ui.button.PeriodButton

@Composable
fun StatisticsScreen(usersViewModel: UsersViewModel = viewModel()) {
    val users by usersViewModel.users

    LaunchedEffect(Unit) {
        usersViewModel.loadUsers()
    }

    val topVisitors = remember(users) {
        users
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Статистика",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        VisitorsSection(visitorCount = users.size)

        Spacer(modifier = Modifier.height(24.dp))

        Visitors(topVisitors = topVisitors)

        Spacer(modifier = Modifier.height(24.dp))

        GenderAgeSection(users = users)

        Spacer(modifier = Modifier.height(24.dp))

        Observers()
    }
}

@Composable
fun VisitorsSection(visitorCount: Int) {
    var selectedPeriod by remember { mutableStateOf("days") }
    Column {
        Text(
            text = "Посетители",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        VisitorsCard(
            visitorCount = 1356,
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
    }
}

@Composable
fun Visitors(topVisitors: List<User>) {
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
            Column {
                topVisitors.forEach { user ->
                    UserCard(user = user)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}


@Composable
fun GenderAgeSection(users: List<User>) {
    val maleCount = users.count { it.sex == "M" }
    val femaleCount = users.count { it.sex == "W" }
    var selectedPeriod by remember { mutableStateOf("today") }
    val total = users.size

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

        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            Text("Мужчины: ")
            Text("${if (total > 0) (maleCount * 100 / total) else 0}%",
                fontWeight = FontWeight.Bold)
        }

        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            Text("Женщины: ")
            Text("${if (total > 0) (femaleCount * 100 / total) else 0}%",
                fontWeight = FontWeight.Bold)
        }

        val ageGroups = mapOf(
            "22-25" to users.count { it.age in 22..25 },
            "26-30" to users.count { it.age in 26..30 },
            "31-35" to users.count { it.age in 31..35 },
            "36-40" to users.count { it.age in 36..40 },
            "40-50" to users.count { it.age in 40..50 },
            ">50" to users.count { it.age > 50 }
        )

        ageGroups.forEach { (group, count) ->
            if (count > 0) {
                Text("$group: $count", modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

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

@Preview(showBackground = true)
@Composable
fun PreviewStatisticsScreen() {
    MaterialTheme {
        StatisticsScreen()
    }
}