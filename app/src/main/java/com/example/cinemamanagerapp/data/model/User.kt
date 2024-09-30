package com.example.cinemamanagerapp.models

import java.util.Date

// ProfileInfo.kt
data class ProfileInfo(
    val address: String? = null,
    val phoneNumber: String? = null, // Đổi tên trường thành phoneNumber
    val gender: String? = null, // Thêm trường giới tính
    val favoriteMovies: List<String>? = null // Có thể lưu ID phim dưới dạng chuỗi
)

// User.kt

data class User(
    val user_id: String,
    val username: String,
    val email: String,
    val role: String,
    val profile_info: ProfileInfo
)


