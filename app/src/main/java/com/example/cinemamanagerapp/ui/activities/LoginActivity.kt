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
import com.example.cinemamanagerapp.api.UserDetails
import com.example.cinemamanagerapp.models.LoginResponse
import com.example.cinemamanagerapp.ui.activities.MainActivity
import com.example.cinemamanagerapp.ui.activities.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerTextView = findViewById(R.id.registerTextView)

        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        checkIfLoggedIn()

        loginButton.setOnClickListener {
            loginUser()
        }

        registerTextView.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun checkIfLoggedIn() {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("user_token", null)
        if (token != null) {
            // Nếu đã đăng nhập, chuyển sang MainActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
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

        RetrofitClient.apiService.loginUser(loginRequest).enqueue { response ->
            if (response.isSuccessful) {
                val loginResponse = response.body()
                loginResponse?.let {
                    val token = it.token
                    saveToken(token)
                    saveUserInfo(it.user)
                    Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } ?: run {
                    Toast.makeText(this, "Đăng nhập thất bại: Không có dữ liệu trả về", Toast.LENGTH_SHORT).show()
                }
            } else {
                val errorMessage = response.errorBody()?.string() ?: response.message()
                Toast.makeText(this, "Đăng nhập thất bại: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserInfo(user: UserDetails?) {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("username", user?.username)
        editor.putString("email", user?.email)
        editor.putString("role", user?.role)
        editor.putString("phone_number", user?.profile_info?.phone_number)
        editor.putString("gender", user?.profile_info?.gender)
        editor.putString("last_login", Date().toString())
        editor.apply()
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
                t.printStackTrace()
            }
        })
    }
}
