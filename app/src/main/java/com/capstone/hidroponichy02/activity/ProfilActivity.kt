package com.capstone.hidroponichy02.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.hidroponichy02.R
import com.capstone.hidroponichy02.databinding.ActivityProfilBinding
import com.capstone.hidroponichy02.model.UserPreference
import com.capstone.hidroponichy02.viewmodel.MainViewModel
import com.capstone.hidroponichy02.viewmodel.ViewModelUserFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class ProfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfilBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelUserFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        binding.back.setOnClickListener {
            navigateUpTo(Intent(this@ProfilActivity, MainActivity::class.java))
            finish()
        }

        binding.btLogout.setOnClickListener {
            mainViewModel.logout()
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.information))
                setMessage(getString(R.string.log_out_success))
                setPositiveButton(getString(R.string.continu)) { _, _ ->
                    startActivity(Intent(this@ProfilActivity, LoginActivity::class.java))
                    finish()
                }
                create()
                show()
            }
        }
    }
}