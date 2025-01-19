package com.example.cinemamanagerapp.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.api.RegisterRequest
import com.example.cinemamanagerapp.api.RegisterResponse
import com.example.cinemamanagerapp.api.RetrofitClient
import com.example.cinemamanagerapp.ui.activities.UtilClass.saveUserInfo
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernameEditText = findViewById(R.id.usernameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username = usernameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        // Kiểm tra thông tin đầu vào
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show()
            return
        }
        val user = RegisterRequest(
            username = username,
            email = email,
            password = password,
        )
        // Gọi API để đăng ký người dùng
        RetrofitClient.apiService.registerUser(user).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.code() == 201) { // khi mã trả về từ 200 - 299, isSucessful = true
                    val registerResponse = response.body()
                    saveUserInfo(this@RegisterActivity,email,password)
                    Toast.makeText(this@RegisterActivity, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    intent.putExtra("userId",registerResponse?.userId)
                    intent.putExtra("userName",registerResponse?.userName)
                    startActivity(intent)
                    finish()
                } else {
                    val errorMessage = response.errorBody()?.string()
                    val gson = Gson()
                    val serverResponse = gson.fromJson(errorMessage, RegisterResponse::class.java)
                    Toast.makeText(this@RegisterActivity, serverResponse.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Lỗi kết nối: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
