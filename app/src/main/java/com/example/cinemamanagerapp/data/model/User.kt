package com.example.cinemamanagerapp.models

data class User(
    val id: String, // ID của người dùng
    val username: String, // Tên người dùng
    val email: String, // Địa chỉ email
    val role: String, // Vai trò của người dùng (ví dụ: admin, user)
    val phone_number: String?, // Số điện thoại (có thể null)
    val gender: String? // Giới tính (có thể null)
)
