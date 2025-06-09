package com.example.statistics

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.statistics.model.ApiService
import com.example.statistics.model.Statistic
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StatisticsViewModel : ViewModel() {
    private val _statistics = mutableStateOf<List<Statistic>>(emptyList())
    val statistics: androidx.compose.runtime.State<List<Statistic>> = _statistics

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://test.rikmasters.ru/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    fun loadStatistics() {
        viewModelScope.launch {
            try {
                _statistics.value = apiService.getStatistics().statistics
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}