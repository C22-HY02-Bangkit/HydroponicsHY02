package com.capstone.hidroponichy02.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.capstone.hidroponichy02.R
import com.capstone.hidroponichy02.databinding.ActivityProfilBinding
import com.capstone.hidroponichy02.databinding.ActivityRegisterBinding
import com.capstone.hidroponichy02.viewmodel.MainViewModel

class ProfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfilBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            onBackPressed()
            finish()
        }
    }
}