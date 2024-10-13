package com.example.cinemamanagerapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.api.RetrofitClient
import com.example.cinemamanagerapp.models.FoodDrink
import com.example.cinemamanagerapp.ui.adapters.FoodDrinkAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout cho fragment
        return inflater.inflate(R.layout.fragment_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview = view.findViewById<RecyclerView>(R.id.rcvFoodDrinkList)

        // gọi dữ liệu từ server
        lifecycleScope.launch {
           try {
               RetrofitClient.apiService.getFoodDrinks().enqueue(object : Callback<List<FoodDrink>>{
                   override fun onResponse(
                       p0: Call<List<FoodDrink>>,
                       p1: Response<List<FoodDrink>>
                   ) {
                       if(p1.isSuccessful){
                           val responseBody = p1.body()!!.toList()
                            val adapter = FoodDrinkAdapter(responseBody)
                           recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
                           recyclerview.adapter = adapter
                       }
                   }

                   override fun onFailure(p0: Call<List<FoodDrink>>, p1: Throwable) {
                       TODO("Not yet implemented")
                   }
               })
           }catch (e: Exception){
               Log.e("TAG", "onViewCreated: $e", )
           }
        }
    }
}
