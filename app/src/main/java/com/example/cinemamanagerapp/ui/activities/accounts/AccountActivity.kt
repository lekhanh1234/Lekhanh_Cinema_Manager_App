package com.example.cinemamanagerapp.ui.activities.accounts

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.api.ApiService
import com.example.cinemamanagerapp.api.RetrofitClient
import com.example.cinemamanagerapp.api.ProfileInfo
import com.example.cinemamanagerapp.api.ApiUser
import com.example.cinemamanagerapp.models.User
import com.google.android.material.textfield.TextInputEditText
import com.squareup.okhttp.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        apiService = RetrofitClient.apiService

        // Lấy thông tin từ SharedPreferences
        loadUserData()

        // Thiết lập sự kiện cho nút Save
        findViewById<Button>(R.id.btnSaveAccount).setOnClickListener {
            updateUserProfile()
        }
    }

    private fun loadUserData() {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")
        val email = sharedPreferences.getString("email", "")
        val phoneNumber = sharedPreferences.getString("phone_number", "")
        val gender = sharedPreferences.getString("gender", "")

        // Điền dữ liệu vào các trường
        findViewById<TextInputEditText>(R.id.tedEmailAccount).setText(email)
        findViewById<TextInputEditText>(R.id.tedNameAccount).setText(username)
        findViewById<TextInputEditText>(R.id.tedPhoneAccount).setText(phoneNumber)

        // Thiết lập giới tính
        when (gender) {
            "Male" -> findViewById<RadioButton>(R.id.rdoMale).isChecked = true
            "Female" -> findViewById<RadioButton>(R.id.rdoFemale).isChecked = true
            else -> findViewById<RadioButton>(R.id.rdoOther).isChecked =
                true // Giả định bạn có RadioButton cho "Other"
        }
    }

    private fun updateUserProfile() {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("user_token", null)

        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Token không hợp lệ", Toast.LENGTH_SHORT).show()
            return
        }

        val authToken = "Bearer $token"
        val username = sharedPreferences.getString("username", "") ?: ""
        val email = sharedPreferences.getString("email", "") ?: ""
        val phoneNumber = findViewById<TextInputEditText>(R.id.tedPhoneAccount).text.toString()
        val gender = when {
            findViewById<RadioButton>(R.id.rdoMale).isChecked -> "Male"
            findViewById<RadioButton>(R.id.rdoFemale).isChecked -> "Female"
            else -> "Other"
        }

        val updatedUser = ApiUser(
            username = username,
            email = email,
            profile_info = ProfileInfo(
                phone_number = phoneNumber,
                gender = gender
            )
        )

        apiService.updateProfile(authToken, updatedUser).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AccountActivity, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                } else {
                    // Improved error handling
                    val errorBody = response.errorBody()?.string()
                    Log.e("AccountActivity", "Update failed: ${response.code()} - $errorBody")
                    val errorMessage = extractErrorMessage(errorBody)
                    Toast.makeText(this@AccountActivity, "Cập nhật thất bại: $errorMessage", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("AccountActivity", "Failed to update user profile", t)
                Toast.makeText(this@AccountActivity, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun extractErrorMessage(errorBody: String?): String {
        // Enhanced error message extraction
        return errorBody?.let {
            val pattern = "<pre>(.*?)</pre>".toRegex()
            pattern.find(it)?.groupValues?.get(1) ?: "Failed to update profile. Please try again later."
        } ?: "An unknown error occurred."
    }

}