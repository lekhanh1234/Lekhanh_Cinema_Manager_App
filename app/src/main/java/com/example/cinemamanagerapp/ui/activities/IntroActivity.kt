package com.example.cinemamanagerapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding // Khai báo biến binding, kiểu ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater) // Sử dụng ViewBinding để lấy layout
        setContentView(binding.root) // Thiết lập nội dung cho Activity bằng root của binding

        // Thiết lập sự kiện khi nhấn nút 'startBtn'
        binding.startBtn.setOnClickListener {
            // Khởi động MainActivity khi nút được nhấn
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Thiết lập cửa sổ cho phép layout toàn màn hình
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, // Cờ cho phép layout không giới hạn
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS // Cờ tương tự, đảm bảo không có giới hạn cho layout
        )
    }
}
