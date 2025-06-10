package com.example.statistics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.statistics.screens.StatisticsScreen
import com.example.statistics.ui.theme.StatisticsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StatisticsTheme {
                StatisticsScreen()
            }
        }
    }
}
