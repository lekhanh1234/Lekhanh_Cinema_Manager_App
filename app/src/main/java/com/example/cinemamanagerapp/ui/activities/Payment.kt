package com.example.cinemamanagerapp.ui.activities

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.api.FoodDrinksResponse
import com.example.cinemamanagerapp.api.RetrofitClient
import com.example.cinemamanagerapp.ui.adapters.ADTDrinkFood
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Payment : AppCompatActivity() {
    private var showtimeId : Int = 0
    private lateinit var currentlySelectedChair: IntArray
    private var ticketPrice: Int = 0 ///
    private lateinit var tvMovieName: TextView
    private lateinit var lv_FoodList: ListView
    private lateinit var adtDrinkFood : ADTDrinkFood //
    private lateinit var tv_paymentSum : TextView //
    private lateinit var cb_zalopay : CheckBox //cb_zalopay //
    private lateinit var btn_payment : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showtimeId = intent.getIntExtra("showtimeId",-1)
        ticketPrice = intent.getIntExtra("ticketPrice",-1)
        currentlySelectedChair = intent.getIntArrayExtra("currentSelectedChair")!!
        tvMovieName = findViewById(R.id.tvMovieName)
        tvMovieName.text = Movie.movieName
        lv_FoodList = findViewById(R.id.lv_FoodList);
        adtDrinkFood = ADTDrinkFood(null,this);
        lv_FoodList.adapter = adtDrinkFood
        cb_zalopay = findViewById(R.id.cb_zalopay)
        tv_paymentSum = findViewById(R.id.tv_paymentSum)
        tv_paymentSum.text = "Tổng thanh toán : "+ticketPrice*currentlySelectedChair.size + " đ"
        btn_payment = findViewById(R.id.btn_payment)
        tv_paymentSum.setOnClickListener({
            payment()
        })
        getDrinkFoodList()
    }

    fun getDrinkFoodList(){
        RetrofitClient.apiService.getAllFoodDrink().enqueue(object : Callback<List<FoodDrinksResponse>>{
            override fun onResponse(
                p0: Call<List<FoodDrinksResponse>>,
                p1: Response<List<FoodDrinksResponse>>
            ) {
                if(p1.code() == 200 || p1.code() == 204){
                    adtDrinkFood.setDrinkFookList(p1.body())
                }else{
                    UtilClass.showErrorAlert(this@Payment,"Error","Thao tác hiện không khả thi !")
                }
            }
            override fun onFailure(p0: Call<List<FoodDrinksResponse>>, p1: Throwable) {
                UtilClass.showErrorAlert(this@Payment,"Error","Xảy ra lỗi !")
            }
        })
    }
    fun updatePaymentSum(){
        var paymentSum = currentlySelectedChair.size * ticketPrice as Double
        val drinkFoodList = adtDrinkFood.getDrinkFookList()
        val map = adtDrinkFood.getDrinkFoodMap()
        if(drinkFoodList == null) return;
        for(element in drinkFoodList){
            if(map.contains(element.foodDrinkId)){
                paymentSum+= element.price * map.get(element.foodDrinkId)!! // price * amount
            }
        }
        tv_paymentSum.text = "Tổng thanh toán : "+paymentSum+" đ";
    }
    fun payment(){
        if(cb_zalopay.isChecked == false){
            UtilClass.showErrorAlert(this,"Thanh toán !","Bạn chưa chọn phương thức thanh toán");
            return
        }else{
              // goi api thanh toan
        }
    }

}