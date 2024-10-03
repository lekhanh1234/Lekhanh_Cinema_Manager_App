package com.example.cinemamanagerapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cinemamanagerapp.R
import com.example.cinemamanagerapp.api.UserProfile
import com.example.cinemamanagerapp.databinding.ActivityMainBinding
import com.example.cinemamanagerapp.ui.fragment.FavoriteFragment
import com.example.cinemamanagerapp.ui.fragment.HomeFragment
import com.example.cinemamanagerapp.ui.fragment.OrderHistoryFragment
import com.example.cinemamanagerapp.ui.fragment.SettingFragment
import com.example.cinemamanagerapp.ui.fragment.StoreFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        var userId : Int = 0 //  0 là chưa được gán. vì userId trong database luon luon > 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId =  intent.getIntExtra("userId",-1)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hiển thị fragment mặc định
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // Thiết lập BottomNavigationView
        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_store -> {
                    replaceFragment(StoreFragment())
                    true
                }
                R.id.nav_history -> {
                    replaceFragment(OrderHistoryFragment())
                    true
                }
                R.id.nav_fav -> {
                    replaceFragment(FavoriteFragment())
                    true
                }
                R.id.nav_settting -> {
                    replaceFragment(SettingFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.home_fragment_activiy, fragment)

        // Không gọi addToBackStack
        transaction.commit()
    }


}
