package com.example.cinemamanagerapp.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.api.LoginRequest
import com.example.cinemamanagerapp.api.LoginResponse
import com.example.cinemamanagerapp.api.RetrofitClient
import com.example.cinemamanagerapp.ui.activities.UtilClass.saveUserInfo
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
        checkLoggedIn()
        setContentView(R.layout.activity_login)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerTextView = findViewById(R.id.registerTextView)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            loginUser(email,password)
        }

        registerTextView.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun checkLoggedIn() {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("password", null) // Trả về null nếu không có giá trị
        if(password != null && email != null){
            loginUser(email,password)
        }
    }

    private fun loginUser(email : String,password : String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show()
            return
        }
        val loginRequest = LoginRequest(email = email, password = password)
        RetrofitClient.apiService.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.code() == 200) {
                    val loginResponse = response.body()
                    loginResponse?.let {
                        saveUserInfo(this@LoginActivity,email,password)
                        Toast.makeText(this@LoginActivity, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                        var intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("userId",loginResponse.id)
                        intent.putExtra("userName",loginResponse.full_name)
                        startActivity(intent)
                        finish()
                    }
                 } else {
                    val errorMessage = response.errorBody()?.string() ?: response.message()
                    Toast.makeText(this@LoginActivity, "Đăng nhập thất bại: $errorMessage", Toast.LENGTH_SHORT).show()
                 }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginActivity", "Lỗi: ${t.message}", t)
                Toast.makeText(this@LoginActivity, "Đã xảy ra lỗi: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
