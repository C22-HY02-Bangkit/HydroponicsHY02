package com.capstone.hidroponichy02.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.capstone.hidroponichy02.R
import com.capstone.hidroponichy02.databinding.ActivityTimelineBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class TimelineActivity : AppCompatActivity() {
        private lateinit var binding: ActivityTimelineBinding
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityTimelineBinding.inflate(layoutInflater)
            setContentView(binding.root)
            val navView: BottomNavigationView = binding.bottom
            navView.setSelectedItemId(R.id.timeline)
            navView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.control -> {
                        startActivity(Intent(applicationContext, ControlActivity::class.java))
                        overridePendingTransition(0, 0)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.dashboard -> {
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        overridePendingTransition(0, 0)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.timeline -> return@OnNavigationItemSelectedListener true
                }
                false
            })
            binding.cam.setOnClickListener{
                startActivity(Intent(this@TimelineActivity, MlActivity::class.java))
                true
            }
            binding.setting.setOnClickListener{
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
        }
    }