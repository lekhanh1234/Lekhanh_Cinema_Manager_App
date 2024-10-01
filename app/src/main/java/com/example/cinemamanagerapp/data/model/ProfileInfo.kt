package com.example.cinemamanagerapp.models

data class ProfileInfo(
    val address: String? = null,
    val phone_number: String? = null,
    val gender: String? = null, // Để gender có thể là null
    val favoriteMovies: List<String>? = null // Sử dụng danh sách rỗng nếu cần
)
