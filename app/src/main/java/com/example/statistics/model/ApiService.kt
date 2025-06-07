package com.example.statistics.model

import retrofit2.http.GET

interface ApiService {
    @GET("users/")
    suspend fun getUsers(): UsersResponse
}