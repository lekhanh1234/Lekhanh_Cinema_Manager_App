package com.example.cinemamanagerapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.api.NotificationResponse
import com.example.cinemamanagerapp.ui.adapters.ADTNotification
import com.example.cinemamanagerapp.api.RetrofitClient
import com.example.cinemamanagerapp.ui.activities.MainActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationFragment  : Fragment() {
    private lateinit var adt_Notification : ADTNotification
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.notification, container, false)
        var listView = view.findViewById<ListView>(R.id.rcv_Notificaton);
        adt_Notification = ADTNotification(null)
        listView.adapter = adt_Notification
        return view;
    }
    fun updateDate(){
        RetrofitClient.apiService.getAllNotification(MainActivity.userId).enqueue(object : Callback<List<NotificationResponse>>{
            override fun onResponse(
                p0: Call<List<NotificationResponse>>,
                p1: Response<List<NotificationResponse>>
            ) {
                if(p1.code() == 200 || p1.code() == 204){
                    adt_Notification.setNewNotification(p1.body())
                }
            }

            override fun onFailure(p0: Call<List<NotificationResponse>>, p1: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}
