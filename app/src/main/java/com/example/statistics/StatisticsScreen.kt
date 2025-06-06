package com.example.statistics

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatisticsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ){
                Text(
                    text = "Статистика",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                VisitorsSection()

                Spacer(modifier = Modifier.height(24.dp))

                GenderAgeSection()
            }
}

@Composable
fun VisitorsSection() {
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
            TextButton(onClick = { /*TODO*/ }) {
                Text("По слову")
            }
            TextButton(onClick = { /*TODO*/ }) {
                Text("По неделям")
            }
            TextButton(onClick = { /*TODO*/ }) {
                Text("По месяцам")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        VisitorInfoItem("21 посетитель", "4 марта")
        VisitorInfoItem("Чаще всех посещают Ваш профиль", "от одного, 25")
        VisitorInfoItem("сентября 2015", "раб. Люка, 32")
    }
}

@Composable
fun VisitorInfoItem(mainText: String, secondaryText: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = mainText, fontWeight = FontWeight.Medium)
        Text(text = secondaryText, color = Color.Gray)
    }
}

@Composable
fun GenderAgeSection() {
    Column {
        Text(
            text = "Пол и возраст",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Таблица
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Сторона")
            Text("Надежь")
            Text("Мысли")
            Text("Все время")
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // Процентное соотношение
        Row(
            modifier = Modifier.padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("В формате: ")
            Text("42%", fontWeight = FontWeight.Bold)
        }

        Row(
            modifier = Modifier.padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Женщина: ")
            Text("60%", fontWeight = FontWeight.Bold)
        }

        // Возрастные группы
        Column(modifier = Modifier.padding(bottom = 16.dp)) {
            AgeGroupItem("22–25")
            AgeGroupItem("26–30")
            AgeGroupItem("31–35")
            AgeGroupItem("36–40")
            AgeGroupItem("40–50")
            AgeGroupItem(">50")
        }

        Text(
            text = "Необходимое количество посетителей выросло",
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "10",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "↓",
                color = Color.Red,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Text(
            text = "Пользователей вырослого",
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
        )

        Text(
            text = "20 Если наблюдать",
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun AgeGroupItem(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewStatisticsScreen() {
    MaterialTheme {
        StatisticsScreen()
    }
}