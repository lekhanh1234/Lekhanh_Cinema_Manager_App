package com.example.cinemamanagerapp.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.api.LoginRequest
import com.example.cinemamanagerapp.api.RetrofitClient
import com.example.cinemamanagerapp.models.LoginResponse
import com.example.cinemamanagerapp.ui.activities.MainActivity
import com.example.cinemamanagerapp.ui.activities.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Đảm bảo layout này tồn tại

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerTextView = findViewById(R.id.registerTextView)

        loginButton.setOnClickListener {
            loginUser()
        }

        registerTextView.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java)) // Chuyển đến RegisterActivity
        }
    }

    private fun loginUser() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        val loginRequest = LoginRequest(email = email, password = password)

        // Sử dụng phương thức mở rộng đã định nghĩa trước đó
        RetrofitClient.apiService.loginUser(loginRequest).enqueue { response ->
            if (response.isSuccessful) {
                val loginResponse = response.body()
                val token = loginResponse?.token
                saveToken(token)
                Toast.makeText(this@LoginActivity, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            } else {
                val errorMessage = response.errorBody()?.string() ?: response.message()
                Toast.makeText(this@LoginActivity, "Đăng nhập thất bại: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveToken(token: String?) {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("user_token", token)
        editor.apply()
    }
    fun <T> Call<T>.enqueue(callback: (Response<T>) -> Unit) {
        this.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback(response)
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                t.printStackTrace() // Xử lý lỗi ở đây nếu cần
            }
        })
    }
}
