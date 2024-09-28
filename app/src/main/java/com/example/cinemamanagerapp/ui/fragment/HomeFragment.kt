package com.example.cinemamanagerapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cinemamanagerapp.R

class HomeFragment : Fragment() {

    private lateinit var nameUserTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout
        val view = inflater.inflate(R.layout.fragment_home_fragment, container, false)

        // Ánh xạ TextView
        nameUserTextView = view.findViewById(R.id.tvNameUser)

        // Lấy tên người dùng và vai trò từ SharedPreferences
        val sharedPreferences =
            requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "Người dùng") // Giá trị mặc định
        val role = sharedPreferences.getString("role", "Quyền")

        // Kiểm tra vai trò và điều chỉnh thông điệp hiển thị
        val welcomeMessage = when (role) {
            "admin" -> "Chào mừng, Quản Trị Viên!\n$username"
            "user" -> "Chào mừng, Bạn\n$username!"
            else -> "Chào mừng, $role\n$username!"
        }

        // Hiển thị thông điệp
        nameUserTextView.text = welcomeMessage

        return view
    }
}
