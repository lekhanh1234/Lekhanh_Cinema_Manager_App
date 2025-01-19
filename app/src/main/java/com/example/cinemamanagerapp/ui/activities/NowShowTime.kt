package com.example.cinemamanagerapp.ui.activities

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.api.RetrofitClient
import com.example.cinemamanagerapp.api.ShowTimeResponse
import com.example.cinemamanagerapp.ui.adapters.ADTShowTimeByCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NowShowTime : AppCompatActivity() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var LL_category_list: LinearLayout//
    private var movieState = 0
    private lateinit var btn_currentMovie: Button
    private lateinit var btn_upcoming_movie: Button
    private lateinit var rcvMovieByCategory: RecyclerView //
    private lateinit var adtShowTimeByCategory: ADTShowTimeByCategory //
    private var categoryList : List<String>? = listOf("abc","bcd","nnn","mmmuuhhh","hhh")
    private var categorySelected : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_now_movie)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            newData()
            swipeRefreshLayout.isRefreshing = false
        }

        btn_currentMovie = findViewById(R.id.btn_upcoming_movie)
        btn_currentMovie.setOnClickListener({
            btn_currentMovie.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_bld))
            btn_upcoming_movie.setBackgroundColor(ContextCompat.getColor(this, R.color.green_chaleston))
            movieState = 0
            if(categorySelected != null) updateShowTime(categorySelected!!)
        })
        btn_upcoming_movie = findViewById(R.id.btn_upcoming_movie)
        btn_upcoming_movie.setOnClickListener({
            btn_upcoming_movie.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_bld))
            btn_currentMovie.setBackgroundColor(ContextCompat.getColor(this, R.color.green_chaleston))
            movieState = 1
            if(categorySelected != null) updateShowTime(categorySelected!!)
        })

        LL_category_list = findViewById(R.id.LL_category_list);
        rcvMovieByCategory = findViewById(R.id.rcvShowTimeByCategory);
        rcvMovieByCategory.adapter = ADTShowTimeByCategory(null,null,this);
        rcvMovieByCategory.layoutManager = GridLayoutManager(this,2)
        newData()
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
                    UtilClass.showErrorAlert(this@NowShowTime,"Error","Thao tác hiện không khả thi !")
                }
            }
            override fun onFailure(p0: Call<List<String>>, p1: Throwable) {
                UtilClass.showErrorAlert(this@NowShowTime,"Error","Thao tác hiện không khả thi !")
            }
        })
    }
    fun updateCategoryUI(){
        LL_category_list.removeAllViews()
        val childCount = categoryList?.size ?: 0
        for (i in 0..childCount) {
            val button =  Button(this).apply {
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
                        childView.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_bld))
                    } else {
                        childView.setBackgroundColor(ContextCompat.getColor(this, R.color.green_chaleston))
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
        RetrofitClient.apiService.getNowShowTime(category).enqueue(object :
            Callback<List<ShowTimeResponse>> {
            override fun onResponse(p0: Call<List<ShowTimeResponse>>, p1: Response<List<ShowTimeResponse>>) {
                if(p1.code() == 200 || p1.code() == 204){
                    val movieList = p1.body();
                    var newList : MutableList<ShowTimeResponse> = mutableListOf()
                    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                    val currentTime = Date()
                    if(movieList != null){
                        for(element in movieList){
                            val movieStartTime: Date = formatter.parse(element.start_time)
                            when {
                                movieStartTime.after(currentTime) -> {
                                    if(movieState == 0) newList.add(element)
                                }
                                movieStartTime.before(currentTime) -> {
                                    if(movieState == 1) newList.add(element)
                                }
                            }
                        }
                    }
                    adtShowTimeByCategory.setCategory(category)
                    adtShowTimeByCategory.setShowTimeList(newList)
                }else{
                    UtilClass.showErrorAlert(this@NowShowTime,"Error","Thao tác hiện không khả thi !")
                }
            }
            override fun onFailure(p0: Call<List<ShowTimeResponse>>, p1: Throwable) {
                UtilClass.showErrorAlert(this@NowShowTime,"Error","Thao tác hiện không khả thi !")
            }
        })
    }
}