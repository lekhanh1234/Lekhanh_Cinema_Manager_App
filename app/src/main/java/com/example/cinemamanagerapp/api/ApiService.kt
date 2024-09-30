package com.example.cinemamanagerapp.api

import com.example.cinemamanagerapp.models.User
import com.squareup.okhttp.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.POST

// Định nghĩa lớp Request cho API đăng nhập
data class LoginRequest(
    val email: String,
    val password: String
)

// Định nghĩa lớp ProfileInfo
data class ProfileInfo(
    val address: String? = null,
    val phone_number: String? = null,
    val gender: String? = null,
    val favorite_movies: List<String>? = null // Có thể lưu ID phim dưới dạng chuỗi
)

// Định nghĩa lớp ApiUser cho đăng ký và cập nhật
data class ApiUser(
    val username: String,
    val email: String,
    val password: String? = null, // Có thể bỏ qua nếu không thay đổi mật khẩu
    val role: String? = null,
    val profile_info: ProfileInfo? = null // Đảm bảo đây là ProfileInfo từ api
)

interface ApiService {
    @POST("auth/login")
    fun loginUser(@Body user: LoginRequest): Call<LoginResponse>

    // Đăng ký người dùng mới
    @POST("auth/register")
    fun registerUser(@Body user: ApiUser): Call<Void>

    @GET("user/profile")
    fun getProfile(): Call<User>

    @PUT("user/profile")
    fun updateProfile(@Header("Authorization") token: String, @Body user: ApiUser): Call<ResponseBody>
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
    val role: String,
    val profile_info: ProfileInfo // Thêm profile_info vào UserDetails
)
