package com.capstone.hidroponichy02.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import com.capstone.hidroponichy02.R
import com.capstone.hidroponichy02.data.Socket
import com.capstone.hidroponichy02.databinding.ActivityControlBinding
import com.capstone.hidroponichy02.databinding.ActivityTimelineBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject

class ControlActivity : AppCompatActivity() {
    private lateinit var binding: ActivityControlBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControlBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.bottom
        navView.setSelectedItemId(R.id.control)
        navView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.control ->
                    return@OnNavigationItemSelectedListener true
                R.id.sensor -> {
                    startActivity(Intent(applicationContext, DetailActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
        binding.back.setOnClickListener{
            navigateUpTo(Intent(this@ControlActivity, MainActivity::class.java))
            true
        }

    }
}