package com.example.cinemamanagerapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.ui.activities.LoginActivity
import com.example.cinemamanagerapp.ui.activities.accounts.AccountActivity

class SettingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout cho fragment
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        // Tìm LinearLayout và thiết lập OnClickListener
        val lnAccount: LinearLayout = view.findViewById(R.id.lnAccount)
        lnAccount.setOnClickListener {
            val intent = Intent(activity, AccountActivity::class.java)
            startActivity(intent)
        }

        // Tìm nút Đăng xuất và thiết lập OnClickListener
        val btnLogout: Button = view.findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            logoutUser()
        }

        return view
    }

    private fun logoutUser() {
        // Xóa thông tin người dùng khỏi SharedPreferences
        val sharedPreferences =
            activity?.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.clear()?.apply()

        // Chuyển hướng về màn hình đăng nhập
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Xóa tất cả các Activity trước đó
        startActivity(intent)
    }
}
