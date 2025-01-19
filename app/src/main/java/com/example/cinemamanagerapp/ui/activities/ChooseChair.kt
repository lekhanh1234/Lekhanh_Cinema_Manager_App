package com.example.cinemamanagerapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.api.RetrofitClient
import com.example.cinemamanagerapp.ui.adapters.ADTChair
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseChair : AppCompatActivity( ){
    private lateinit var tv_startTime : TextView
    private lateinit var rcvChairNumber : RecyclerView //btn_to_payment
    private lateinit var btn_to_payment : Button // (null);
    private lateinit var tvPaymentSum : TextView // (null);
    private lateinit var adt_Chair : ADTChair
    private var showtimeId = 0;
    private var ticketPrice = 0;
    private var startTime = ""
    fun getTicketPrice() : Int{
        return ticketPrice
    }
//
    fun getPaymentSumTV() : TextView{
        return tvPaymentSum
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_choose_chair)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showtimeId = intent.getIntExtra("showtimeId",-1)
        ticketPrice = intent.getIntExtra("ticketPrice",-1)
        startTime = intent.getStringExtra("startTime")!!

        tv_startTime = findViewById(R.id.tv_startTime)
        tv_startTime.text = startTime
        rcvChairNumber = findViewById(R.id.rcv_ChairList);
        adt_Chair = ADTChair(null,this)
        rcvChairNumber.adapter = adt_Chair
        rcvChairNumber.layoutManager = GridLayoutManager(this,10)
        tvPaymentSum  = findViewById(R.id.tvPaymentSum)
        btn_to_payment = findViewById(R.id.btn_to_payment)
        btn_to_payment.setOnClickListener({
            var intent = Intent(this,Payment::class.java);
            val currentlySelectedChair = adt_Chair.getCurrentlySelectedChair().toTypedArray()
            intent.putExtra("showtimeId",showtimeId)
            intent.putExtra("currentSelectedChair", currentlySelectedChair)
            intent.putExtra("ticketPrice", ticketPrice)
            startActivity(intent)
        })
        getChooseChair(showtimeId)
    }
    fun getChooseChair(showtimeId : Int){
        RetrofitClient.apiService.getSeatByShowTimeId(showtimeId).enqueue(object : Callback<List<Int>>{
            override fun onResponse(p0: Call<List<Int>>, p1: Response<List<Int>>) {
                if(p1.code() == 200 || p1.code() == 204){
                    var data = p1.body()
                    adt_Chair.setNewSelectedSeList(data)
                }
            }
            override fun onFailure(p0: Call<List<Int>>, p1: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}