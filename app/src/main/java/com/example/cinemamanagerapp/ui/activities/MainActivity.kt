package com.example.cinemamanagerapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemamanagerapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var welcomeTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Đảm bảo tạo layout này

    }
}
