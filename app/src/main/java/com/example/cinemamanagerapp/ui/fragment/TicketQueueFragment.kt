package com.example.cinemamanagerapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.api.RetrofitClient
import com.example.cinemamanagerapp.api.ShowTimeResponse
import com.example.cinemamanagerapp.ui.activities.MainActivity
import com.example.cinemamanagerapp.ui.activities.UtilClass
import com.example.cinemamanagerapp.ui.adapters.ADTTicketQueue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TicketQueueFragment : Fragment() {
    private lateinit var lvTicketQueue : ListView
    private lateinit var adtTicketQueue : ADTTicketQueue


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ticketqueue, container, false)
        lvTicketQueue = view.findViewById(R.id.lv_ticket_queue);
        adtTicketQueue = ADTTicketQueue(null,requireContext())
        lvTicketQueue.adapter = adtTicketQueue
        updateDate()
        return view
    }
    fun updateDate(){
        RetrofitClient.apiService.getAllTicketQueue(MainActivity.userId).enqueue(object : Callback<List<ShowTimeResponse>>{
            override fun onResponse(
                p0: Call<List<ShowTimeResponse>>,
                p1: Response<List<ShowTimeResponse>>
            ) {
                if(p1.code() == 200 || p1.code() == 204){
                    if(p1.body() != null && p1.body() != adtTicketQueue.getData()){
                        adtTicketQueue.setNewData(p1.body())
                    }
                }else{
                    UtilClass.showErrorAlert(requireContext(),"Error","Thao tác hiện không khả thi !")
                }
            }
            override fun onFailure(p0: Call<List<ShowTimeResponse>>, p1: Throwable) {
                UtilClass.showErrorAlert(requireContext(),"Error","Thao tác hiện không khả thi !")
            }

        })
    }
}
