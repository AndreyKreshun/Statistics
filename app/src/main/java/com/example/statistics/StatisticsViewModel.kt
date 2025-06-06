package com.example.statistics

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.statistics.model.ApiService
import com.example.statistics.model.Statistic
import com.example.statistics.model.User
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StatisticsViewModel: ViewModel() {
    private val _users = mutableStateOf<List<User>>(emptyList())
    val users: State<List<User>> = _users

    private val _statistics = mutableStateOf<List<Statistic>>(emptyList())
    val statistics: State<List<Statistic>> = _statistics

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://test.rikmasters.ru/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    fun loadData() {
        viewModelScope.launch {
            try {
                _users.value = apiService.getUsers().users
                _statistics.value = apiService.getStatistics().statistics
            } catch (e: Exception) {
                // Обработка ошибок
                e.printStackTrace()
            }
        }
    }

    fun getViewCount(): Int {
        return statistics.value.filter { it.type == "view" }.sumOf { it.dates.size }
    }

    fun getGenderDistribution(): Pair<Int, Int> {
        val maleCount = users.value.count { it.sex == "M" }
        val femaleCount = users.value.count { it.sex == "W" }
        return Pair(maleCount, femaleCount)
    }

    fun getAgeDistribution(): Map<String, Int> {
        return users.value.groupBy {
            when (it.age) {
                in 22..25 -> "22-25"
                in 26..30 -> "26-30"
                in 31..35 -> "31-35"
                in 36..40 -> "36-40"
                in 40..50 -> "40-50"
                else -> ">50"
            }
        }.mapValues { it.value.size }
    }
}