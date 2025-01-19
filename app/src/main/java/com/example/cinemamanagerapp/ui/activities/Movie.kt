package com.example.cinemamanagerapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.api.MovieInfoResponse
import com.example.cinemamanagerapp.api.RetrofitClient
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Movie : AppCompatActivity() {
    companion object{
        var movieId : Int = 0
        var movieName : String = ""
    }
    private var showtimeId = 0; //
    private var ticketPrice = 0;
    private var startTime =""

    private lateinit var videoMovie : StyledPlayerView //
    private lateinit var tvMovieName : TextView
    private lateinit var tvGenre : TextView
    private lateinit var tvPlot : TextView
    private lateinit var IMG_addTickQueqe : ImageButton
    private lateinit var BTN_BookTickets : Button //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showtimeId = intent.getIntExtra("showtimeId",-1)
        movieId = intent.getIntExtra("movieId",-1)
        ticketPrice = intent.getIntExtra("ticketPrice",-1)
        startTime = intent.getStringExtra("startTime")!!

        videoMovie = findViewById(R.id.videoMovie)
        tvMovieName = findViewById(R.id.tvMovieName)
        tvGenre = findViewById(R.id.tvGenre)
        tvPlot = findViewById(R.id.tvPlot)
        IMG_addTickQueqe = findViewById(R.id.IMG_addTickQueqe)
        BTN_BookTickets = findViewById(R.id.BTN_BookTickets)
        videoMovie.isClickable = false
        IMG_addTickQueqe.setOnClickListener({
            // thêm bảng TickQueqe trên server
        })
        BTN_BookTickets.setOnClickListener({
            var intent = Intent(this,ChooseChair::class.java)
            intent.putExtra("showtimeId",showtimeId)
            intent.putExtra("ticketPrice",ticketPrice) //
            intent.putExtra("startTime",startTime)
            startActivity(intent)
        })
        getData(movieId)
    }
    fun getData(movieId : Int){
        RetrofitClient.apiService.getMovieById(movieId).enqueue(object : Callback<MovieInfoResponse>{
            override fun onResponse(p0: Call<MovieInfoResponse>, p1: Response<MovieInfoResponse>) {
                if(p1.code() == 200){
                    var data = p1.body()
                    var player = ExoPlayer.Builder(this@Movie).build()
                    videoMovie.player = player
                    val link = "http://192.168.75.1:3001/myvideo.mp4" //thay = url tu server
                    var meddia = MediaItem.fromUri(link)
                    player.setMediaItem(meddia)
                    player.prepare()
                    player.play()
                    tvMovieName.text = data!!.title
                    movieName = data!!.title
                    tvGenre.text = intent.getStringExtra("category")
                    tvPlot.text = data!!.description
                }
            }
            override fun onFailure(p0: Call<MovieInfoResponse>, p1: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
}