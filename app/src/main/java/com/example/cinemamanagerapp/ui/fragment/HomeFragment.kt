package com.example.cinemamanagerapp.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.api.RetrofitClient
import com.example.cinemamanagerapp.api.ShowTimeResponse
import com.example.cinemamanagerapp.ui.activities.MainActivity
import com.example.cinemamanagerapp.ui.activities.NowShowTime
import com.example.cinemamanagerapp.ui.activities.UtilClass
import com.example.cinemamanagerapp.ui.adapters.ADTShowTimeByCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var nameUserTextView: TextView
    private lateinit var rcvShowTimeByCategory: RecyclerView //
    private lateinit var adtShowTimeByCategory: ADTShowTimeByCategory
    private lateinit var tv_ToNowMovie: TextView
    private lateinit var LL_category_list: LinearLayout
    private var categoryList : List<String>? = listOf("abc","bcd","nnn","mmmuuhhh","hhh")
    private var categorySelected : String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_fragment, container, false)
        nameUserTextView = view.findViewById(R.id.tvNameUser)
        nameUserTextView.text = MainActivity.userName
        tv_ToNowMovie = view.findViewById(R.id.tv_ToNowMovie);
        tv_ToNowMovie.setOnClickListener(View.OnClickListener { startActivity(Intent(context,NowShowTime::class.java)) })
        LL_category_list = view.findViewById(R.id.LL_category_list)
        rcvShowTimeByCategory = view.findViewById(R.id.rcvShowTimeByCategory);
        adtShowTimeByCategory = ADTShowTimeByCategory(null,null,requireContext());
        rcvShowTimeByCategory.adapter = adtShowTimeByCategory
        rcvShowTimeByCategory.layoutManager = GridLayoutManager(context,2)
        ////////////////////////////
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            newData()
            swipeRefreshLayout.isRefreshing = false
        }
        newData()
        return view
    }

    fun newData(){
        RetrofitClient.apiService.getAllCategory().enqueue(object : Callback<List<String>>{
            override fun onResponse(p0: Call<List<String>>, p1: Response<List<String>>) {
                if(p1.code() == 200 || p1.code() == 204){
                    var newCategoryList = p1.body()
                    if(newCategoryList != categoryList){
                        categoryList = newCategoryList
                        updateCategoryUI()
                    }else{
                        if(categorySelected != null)
                        updateShowTime(categorySelected!!)
                    }
                }else{
                    UtilClass.showErrorAlert(requireContext(),"Error","Thao tác hiện không khả thi !")
                }
            }
            override fun onFailure(p0: Call<List<String>>, p1: Throwable) {
                   UtilClass.showErrorAlert(requireContext(),"Error","Thao tác hiện không khả thi !")
            }
        })
    }
    fun updateCategoryUI(){
        LL_category_list.removeAllViews()
        val childCount = categoryList?.size ?: 0
        for (i in 0..childCount) {
            val button =  Button(requireContext()).apply {
                setBackgroundColor(resources.getColor(R.color.blue_bld))
                layoutParams = LinearLayout.LayoutParams(350, LinearLayout.LayoutParams.MATCH_PARENT) // Width: 90dp, Height: match_parent
                setPadding(0, 0, 3, 0) // Margin right 3dp
                setTextSize(13f)
                isAllCaps = false
            }
            if (i == childCount) button.text = "all"
            else button.text = "${categoryList!!.get(i)}"
            button.setOnClickListener({
                for (i in 0 .. childCount ) {
                    val childView = LL_category_list.getChildAt(i) as Button
                    if (childView === button) {
                        childView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue_bld))
                    } else {
                        childView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_chaleston))
                    }
                }
                categorySelected = if (i < childCount) categoryList!!.get(i) else "all"
                updateShowTime(categorySelected!!)
            })
            LL_category_list.addView(button)
        }
        categorySelected = categoryList?.get(0) ?: "all"
        updateShowTime(categorySelected!!)
    }

    fun updateShowTime(category : String){
         RetrofitClient.apiService.getShowTimeCategory(category).enqueue(object : Callback<List<ShowTimeResponse>>{
            override fun onResponse(p0: Call<List<ShowTimeResponse>>, p1: Response<List<ShowTimeResponse>>) {
                if(p1.code() == 200 || p1.code() == 204){
                    var movieList = p1.body();
                    adtShowTimeByCategory.setCategory(category)
                    adtShowTimeByCategory.setShowTimeList(movieList)
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
