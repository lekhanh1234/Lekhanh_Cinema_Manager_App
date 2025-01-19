package com.example.cinemamanagerapp.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    fun loginUser(@Body user: LoginRequest): Call<LoginResponse>

    @POST("auth/register")
    fun registerUser(@Body user: RegisterRequest): Call<RegisterResponse>

    @PUT("auth/password/update")
    fun updatePassword(@Body passwordRequest: PasswordUpdateRequest): Call<Void>

    @GET("api/notification/all/{id}")
    fun getAllNotification(@Path("id") id: Int): Call<List<NotificationResponse>>


    @GET("api/profile/{id}")
    fun getProfile(@Path("id") id: Int): Call<UserProfileResponse>

    @PUT("api/profile/update/{id}")
    fun updateProfile(@Path("id") id: Int,@Body user: UserProfileEditRequest): Call<Void>

    @GET("api/category/get-all")
    fun getAllCategory(): Call<List<String>>

    @GET("api/ticket-queue/get-all/{usedId}")
    fun getAllTicketQueue(@Path("usedId") userId: Int): Call<List<ShowTimeResponse>>


    @GET("api/showtime/{category}")
    fun getShowTimeCategory(@Path("category") category: String): Call<List<ShowTimeResponse>>


    @GET("api/movie/{id}")
    fun getMovieById(@Path("id") id: Int): Call<MovieInfoResponse>

    @GET("api/choose-seat/{showtime-id}") // thêm api này (2/11)
    fun getSeatByShowTimeId(@Path("showtime-id") showtimeId: Int): Call<List<Int>> // danh sach so ghe duoc chon

    @GET("api/showtime/now/{category}")
    fun getNowShowTime(@Path("category") category: String): Call<List<ShowTimeResponse>> // các phim chuẩn bị chiếu trong ngày. select database -> filter

    @GET("api/food-drink/all")
    fun getAllFoodDrink(): Call<List<FoodDrinksResponse>>

    @POST("api/payment/payment/{showtime-id}")
    fun PaymentSum(@Path("showtime-id") showtimeId: Int,@Body data : PaymentSumRequest): Call<PaymentSumResponse>

    @GET("api/review/{movie-id}")
    fun getAllReviewMovie(@Path("movie-id") movieId: Int): Call<List<ReviewResponse>>

    @PUT("api/review/submit/{showtimeId}")
    fun getSubmitReview(@Path("showtimeId") showtimeId: Int,@Body review: ReviewRequest ): Call<Void> // 204 // suscess but no ResponseData

    @DELETE("api/review/delete/{reviewId}")
    fun deleteReView(@Path("reviewId") reviewId: Int): Call<Void>

    @GET("api/history/{userId}")
    fun getAllHistory(@Path("userId") userId: Int): Call<BookingHistoryResponse>

}