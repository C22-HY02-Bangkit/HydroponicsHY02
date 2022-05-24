package com.capstone.hidroponichy02.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.capstone.hidroponichy02.databinding.ActivitySplashBinding
import com.capstone.hidroponichy02.viewmodel.MainViewModel
import com.capstone.hidroponichy02.viewmodel.ViewModelUserFactory
import com.example.storyapp.model.UserPreference
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private var mShouldFinish = false
    private lateinit var splashActivityBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashActivityBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashActivityBinding.root)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    splashActivityBinding.splash,
                    "logoLogin"
                )
            startIntent()
            mShouldFinish = true
        }, SPLASH_DELAY)
    }

    private fun startIntent() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelUserFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        lifecycleScope.launchWhenCreated {
            launch {
                mainViewModel.getUser().collect {
                    if (it.isLogin) {
                        goMain(true)
                    } else goMain(false)
                }
            }
        }
    }

    private fun goMain(boolean: Boolean) {
        if (boolean) {
            startActivity(
                Intent(this, MainActivity::class.java),
                ActivityOptionsCompat.makeSceneTransitionAnimation(this as Activity).toBundle()
            )
            finish()
        } else {
            startActivity(
                Intent(this, LoginActivity::class.java),
                ActivityOptionsCompat.makeSceneTransitionAnimation(this as Activity).toBundle()
            )
            finish()
        }
    }
    override fun onStop() {
        super.onStop()
        if (mShouldFinish)
            finish()
    }
    companion object {
        const val SPLASH_DELAY = 2000L
    }
}
