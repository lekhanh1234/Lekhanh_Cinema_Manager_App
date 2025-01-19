package com.example.cinemamanagerapp.ui.activities

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog

object UtilClass {
        fun saveUserInfo(context: Context,email : String , password: String) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
        Log.d("LoginActivity", "User ID saved: ${email} --- ${password}")
        }
        fun showErrorAlert(context: Context,title : String,message : String) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("Ok") { dialog, _ ->
                                dialog.dismiss() // Đóng thông báo khi nhấn Đồng ý
                        }
                val alertDialog = builder.create()
                alertDialog.show()
        }
}
