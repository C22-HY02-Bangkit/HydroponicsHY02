package com.capstone.hidroponichy02.activity

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.capstone.hidroponichy02.R
import com.capstone.hidroponichy02.adapter.SectionPagerAdapter
import com.capstone.hidroponichy02.databinding.ActivityMainBinding
import com.capstone.hidroponichy02.model.UserModel
import com.capstone.hidroponichy02.model.UserPreference
import com.capstone.hidroponichy02.viewmodel.MainViewModel
import com.capstone.hidroponichy02.viewmodel.ViewModelUserFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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
        val navView: BottomNavigationView = binding.bottom
        val mBundle = Bundle()

        with(navView) {

            setSelectedItemId(R.id.dashboard)
            setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.control -> {
                        startActivity(Intent(applicationContext, ControlActivity::class.java))
                        overridePendingTransition(0, 0)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.dashboard -> return@OnNavigationItemSelectedListener true
                    R.id.timeline -> {
                        startActivity(Intent(applicationContext, TimelineActivity::class.java))
                        overridePendingTransition(0, 0)
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            })
            binding.faddstory.setOnClickListener{
                startActivity(Intent(this@MainActivity, ProfilActivity::class.java))
                true
            }
            binding.cam.setOnClickListener{
                startActivity(Intent(this@MainActivity, MlActivity::class.java))
                true
            }
            binding.setting.setOnClickListener{
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, mBundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabLayout: TabLayout = binding.tabLayout

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        supportActionBar?.hide()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        menu.findItem(R.id.add).isVisible = false
        menu.findItem(R.id.maps).isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}