package com.example.statistics.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.statistics.ui.components.GenderAgeSection
import com.example.statistics.ui.components.Observers
import com.example.statistics.ui.components.Users
import com.example.statistics.ui.components.VisitorsSection
import com.example.statistics.viewmodels.StatisticsViewModel
import com.example.statistics.viewmodels.UsersViewModel

@Composable
fun StatisticsScreen(usersViewModel: UsersViewModel = viewModel(),
                     statisticsViewModel: StatisticsViewModel = viewModel()) {
    val users by usersViewModel.users
    val statistics by statisticsViewModel.statistics

    LaunchedEffect(Unit) {
        usersViewModel.loadUsers()
        statisticsViewModel.loadStatistics()
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

        VisitorsSection(visitorCount = users.size,
            statistics = statistics)

        Spacer(modifier = Modifier.height(24.dp))

        Users(topVisitors = topVisitors, statistics = statistics)

        Spacer(modifier = Modifier.height(24.dp))

        GenderAgeSection(users = users, statistics = statistics)

        Spacer(modifier = Modifier.height(24.dp))

        Observers()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStatisticsScreen() {
    MaterialTheme {
        StatisticsScreen()
    }
}