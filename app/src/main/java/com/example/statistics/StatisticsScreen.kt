package com.example.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.statistics.model.User
import com.example.statistics.ui.button.PeriodButton
import com.example.statistics.ui.theme.femaleColor
import com.example.statistics.ui.theme.maleColor

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
    var selectedPeriod by remember { mutableStateOf("today") }
    val totalUsers = users.size
    val maleCount = users.count { it.sex == "M" }
    val femaleCount = users.count { it.sex == "W" }
    val malePercentage = if (totalUsers > 0) (maleCount * 100f / totalUsers) else 0f
    val femalePercentage = if (totalUsers > 0) (femaleCount * 100f / totalUsers) else 0f

    Column {
        Text(
            text = "Пол и возраст",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Кнопки выбора периода
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
                    maleColor = maleColor,
                    femaleColor = femaleColor
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            GenderLegendItem(color = maleColor, label = "Мужчины", percentage = malePercentage)
            Spacer(modifier = Modifier.width(16.dp))
            GenderLegendItem(color = femaleColor, label = "Женщины", percentage = femalePercentage)
        }

        val ageGroups = listOf(
            "До 18" to users.count { it.age < 18 },
            "18-21" to users.count { it.age in 18..21 },
            "22-25" to users.count { it.age in 22..25 },
            "26-30" to users.count { it.age in 26..30 },
            "31-35" to users.count { it.age in 31..35 },
            "36-40" to users.count { it.age in 36..40 },
            "41-50" to users.count { it.age in 41..50 },
            "50+" to users.count { it.age > 50 }
        )

        ageGroups.forEach { (group, count) ->
            if (count > 0) {
                AgeGroupItem(
                    ageGroup = group,
                    percentage = if (totalUsers > 0) (count * 100f / totalUsers) else 0f
                )
            }
        }
    }
}

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

        // Мужчины
        drawArc(
            color = maleColor,
            startAngle = -90f,
            sweepAngle = sweepMale,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            size = Size(diameter, diameter),
            topLeft = Offset(0f, 0f)
        )

        // Женщины
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


@Composable
fun GenderLegendItem(color: Color, label: String, percentage: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "$label ${"%.0f".format(percentage)}%",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Composable
fun AgeGroupItem(ageGroup: String, percentage: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = ageGroup,
            modifier = Modifier.width(60.dp),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.width(8.dp))

        LinearProgressIndicator(
            progress = percentage / 100f,
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primaryContainer
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "${"%.1f".format(percentage)}%",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
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