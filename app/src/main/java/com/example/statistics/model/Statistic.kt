package com.example.statistics.model

data class Statistic(
    val user_id: Int,
    val type: String,
    val dates: List<Int>
)
