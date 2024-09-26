package com.example.cinemamanagerapp.models

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
