package com.example.statistics

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.statistics.model.ApiService
import com.example.statistics.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsersViewModel : ViewModel() {
    private val _users = mutableStateOf<List<User>>(emptyList())
    val users = _users

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://test.rikmasters.ru/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    fun loadUsers() {
        viewModelScope.launch {
            try {
                _users.value = apiService.getUsers().users
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
