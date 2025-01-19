package com.example.cinemamanagerapp.api

data class LoginRequest(
    val email: String,
    val password: String
)
data class RegisterRequest(
    val email: String,
    val password: String,
    val username: String
)
data class ReviewRequest(
    val bookTicketId : Int,
    val comment: String,
    val rate : Int,
)
data class PasswordUpdateRequest(
    val email: String,
    val oldPassword: String,
    val newPassword: String
)
data class UserProfileEditRequest(
    val user_id : Int,
    val email: String,
    val username: String,
    val phone_number: String? = null,
    val age : Int? = null,
    val address: String? = null,
    val gender: String,
)

data class PaymentSumRequest(
    val showtimeId : Int,
    val paymentMethod : String,
    val seatNumbers: List<Int>,
    val drinkFood : List<DrinkFood>,
    val estimatedPayment : Double // lưu ý đây là phần ước tính,vi co the co nhieu kha nang xay ra
    // => neu ghe da duoc chon, co the tra ve ma 409, message : Error with Selected Seats
    // client se theo loi nay su li tiep
    //tuong tu => neu admin thay doi gia, delete showtime , co the tra ve ma 409, message : Error with Price Change
    // tại server tính lại giá 1 lần nua tu database dua vao nhung thong tin tren, neu k co su thay doi
    // va seatNumbers chua duoc chon, => hoan thanh => status 201
)

data class DrinkFood(
    val drinkFoodId : Int,
    val amount : Int,
)