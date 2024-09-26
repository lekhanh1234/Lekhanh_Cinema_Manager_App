package com.example.cinemamanagerapp.models

import java.util.Date

data class ProfileInfo(
    val address: String? = null,
    val phoneNumber: String? = null,
    val favoriteMovies: List<String>? = null // Có thể lưu ID phim dưới dạng chuỗi
)

data class User(
    val username: String,
    val email: String,
    val password: String,
    val role: String? = null // Nếu role không cần thiết, hãy loại bỏ
)

