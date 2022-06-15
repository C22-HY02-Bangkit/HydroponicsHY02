package com.capstone.hidroponichy02.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.hidroponichy02.R
import com.capstone.hidroponichy02.databinding.ActivityProfilBinding
import com.capstone.hidroponichy02.databinding.ActivityRegisterBinding

class ProfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            navigateUpTo(Intent(this@ProfilActivity, MainActivity::class.java))
            finish()
        }
    }
}