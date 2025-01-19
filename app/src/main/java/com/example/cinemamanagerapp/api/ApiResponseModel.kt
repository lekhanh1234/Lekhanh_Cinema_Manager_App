package com.example.cinemamanagerapp.api

data class LoginResponse(
    val id: Int,
    val full_name : String,
    val phone_number : String?,
    val email : String,
    val active : Boolean,
    val created_at : String,
    val updated_at : String

)
data class RegisterResponse(
    val message: String,
    val userId : Int,
    val userName : String,
    val email : String,
    val createdAt : String
)
data class FoodDrinksResponse(
    val foodDrinkId : Int,
    val name: String,
    val type : String,
    val created_at: String,
    val update_at : String,
    val price : Double,
    val imgUrl : String
)
data class ReviewResponse(
    val review_id : Int,
    val comment: String,
    val rate : Int,
)

data class NotificationResponse(
    val notification_id : Int,
    val user_id : Int,
    val message : String,     // Tham số password
    val created_at : String,
)

data class UserProfileResponse(
    val user_id : Int,
    val email: String,        // Tham số email
    val password: String,     // Tham số password
    val username: String,
    val phone_number: String? = null,
    val age : Int? = null,
    val address: String? = null,
    val gender: String,
    val created_at : String,
    val last_login : String,
   //  val avatar_url : String , xem cần thêm  1 field "avatar_url" trong database k, có thì thêm vào rôi add->response
)
data class MovieInfoResponse(
    val title : String,
    val description : String,
    val trailer_url : String,
    val category_id : Int,
    val duration : Int,
    val release_date : String
)
data class ShowTimeResponse(
    val showTimeId : Int = 0,
    val start_time : String,
    val room : Int = 0,
    val ticket_price : Int = 0,
    val movie : ShowTimeMovieInfo
)
data class ShowTimeMovieInfo(
    val id : Int = 0,
    val name : String = "",
    val img_url : String = ""
)
data class BookingHistoryResponse(
    val idShowTime : Int,
    val movieName : String = "",
    val ticketAmount : Int,
    val showTime : String = "", // thời gian trình chiếu : 2021-12-05 12:22:11
    val payment : Int
)

data class PaymentSumResponse(
    val message : String
)