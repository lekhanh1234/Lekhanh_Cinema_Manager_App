package com.example.cinemamanagerapp.api

import com.example.cinemamanagerapp.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Định nghĩa lớp Request cho API đăng nhập
data class LoginRequest(
    val email: String,
    val password: String
)

// Định nghĩa lớp ApiUser cho đăng ký
data class ApiUser(
    val username: String,
    val email: String,
    val password: String,
    val role: String? = null
)

interface ApiService {
    @POST("auth/login")
    fun loginUser(@Body user: LoginRequest): Call<LoginResponse>


    // Register a new user
    @POST("auth/register")
    fun registerUser(@Body user: User): Call<Void>
}


// Data class cho phản hồi đăng nhập
data class LoginResponse(
    val token: String,
    val user: UserDetails
)

data class UserDetails(
    val user_id: String,
    val username: String,
    val email: String,
    val role: String
)
