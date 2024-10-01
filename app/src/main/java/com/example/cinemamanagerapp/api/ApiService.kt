package com.example.cinemamanagerapp.api

import com.example.cinemamanagerapp.models.ProfileInfo
import com.example.cinemamanagerapp.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

// Định nghĩa lớp Request cho API đăng nhập
data class LoginRequest(
    val email: String,
    val password: String
)

data class UserIdRequest(val email: String)

data class PasswordRequest(
    val email: String,
    val oldPassword: String,
    val newPassword: String
)

data class ApiUser(
    val userId: String,
    val username: String,
    val email: String,
    val password: String, // Thêm trường password
    val address: String? = null,
    val phone_number: String? = null,
    val gender: String? = null
)


interface ApiService {
    @POST("auth/login")
    fun loginUser(@Body user: LoginRequest): Call<LoginResponse>

    @POST("auth/register")
    fun registerUser(@Body user: ApiUser): Call<Void>

    @POST("auth/get-profile")
    fun getProfile(@Body userIdRequest: UserIdRequest): Call<UserDetails>

    @PUT("auth/update-profile")
    fun updateProfile(@Body user: ApiUser): Call<Void>

    @PUT("auth/update-password")
    fun updatePassword(@Body passwordRequest: PasswordRequest): Call<Void>
}

// Data class cho phản hồi đăng nhập
data class LoginResponse(
    val token: String,
    val user: UserDetails
)

data class UserDetails(
    val id: String,
    val username: String,
    val email: String,
    val role: String, // Đảm bảo thuộc tính này có trong UserDetails
    val address: String? = null,
    val phone_number: String? = null,
    val gender: String? = null
)
