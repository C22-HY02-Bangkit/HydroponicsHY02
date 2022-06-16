package com.capstone.hidroponichy02.activity

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.capstone.hidroponichy02.R
import com.capstone.hidroponichy02.databinding.ActivityMainBinding
import com.capstone.hidroponichy02.model.UserModel
import com.capstone.hidroponichy02.model.UserPreference
import com.capstone.hidroponichy02.viewmodel.MainViewModel
import com.capstone.hidroponichy02.viewmodel.ViewModelUserFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class MainActivity : AppCompatActivity() {
    private lateinit var user: UserModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            val moveToListDeviceActivity = Intent(this@MainActivity, DeviceListActivity::class.java)
            moveToListDeviceActivity.putExtra(DeviceListActivity.EXTRA_USER, user)
            startActivity(moveToListDeviceActivity)
        }

        binding.btnLogOut.setOnClickListener {
            mainViewModel.logout()
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.information))
                setMessage(getString(R.string.log_out_success))
                setPositiveButton(getString(R.string.continu)) { _, _ ->
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()
                }
                create()
                show()
            }
        }
        setViewModel()
        playImgAnimation()
    }

    private fun setViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelUserFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        lifecycleScope.launchWhenCreated {
            launch {
                mainViewModel.getUser().collect {
                    user = it
                    binding.txtName.text = getString(R.string.greeting, user.fullname)
                }
            }
        }

    }

    private fun playImgAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_Y, 12f, -24f).apply {
            duration = 5000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

    }

}