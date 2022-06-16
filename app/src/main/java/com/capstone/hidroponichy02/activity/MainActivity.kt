package com.capstone.hidroponichy02.activity

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.capstone.hidroponichy02.R
import com.capstone.hidroponichy02.adapter.DeviceAdapter
import com.capstone.hidroponichy02.adapter.LoadingStateAdapter
import com.capstone.hidroponichy02.adapter.SectionPagerAdapter
import com.capstone.hidroponichy02.databinding.ActivityMainBinding
import com.capstone.hidroponichy02.model.UserModel
import com.capstone.hidroponichy02.model.UserPreference
import com.capstone.hidroponichy02.viewmodel.DeviceViewModel
import com.capstone.hidroponichy02.viewmodel.MainViewModel
import com.capstone.hidroponichy02.viewmodel.ViewModelFactory
import com.capstone.hidroponichy02.viewmodel.ViewModelUserFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class MainActivity : AppCompatActivity() {
    private lateinit var user: UserModel
    private lateinit var adapter: DeviceAdapter
    private lateinit var mainViewModel: MainViewModel
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private val viewModel: DeviceViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()
        user = intent.getParcelableExtra(EXTRA_USER)!!
        val navView: BottomNavigationView = binding!!.bottom

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

            binding!!.setting.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
        }

        adapter()
        swipeToRefresh()
        binding!!.faddstory.setOnClickListener{
            startActivity(Intent(this@MainActivity, ProfilActivity::class.java))
            true
        }
        binding!!.cam.setOnClickListener{
            startActivity(Intent(this@MainActivity, MlActivity::class.java))
            true
        }
        binding!!.setting.setOnClickListener{
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            true
        }
    }

    private fun adapter() {

        adapter = DeviceAdapter()
        binding?.rvStory?.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        binding?.rvStory?.layoutManager = LinearLayoutManager(this@MainActivity)
        binding?.rvStory?.setHasFixedSize(true)
        lifecycleScope.launchWhenStarted {
            adapter.loadStateFlow.collect {
                 binding?.swipeRefresh?.isRefreshing = it.mediator?.refresh is LoadState.Loading
                 if (adapter.itemCount < 1) binding?.viewError?.root?.visibility = View.VISIBLE
                 else binding?.viewError?.root?.visibility = View.GONE
                 }
                }
                lifecycleScope.launch {
                    adapter.loadStateFlow.collectLatest { loadStates ->
                        binding?.viewError?.root?.isVisible = loadStates.refresh is LoadState.Error
                    }
                    if (adapter.itemCount < 1) binding?.viewError?.root?.visibility = View.VISIBLE
                    else binding?.viewError?.root?.visibility = View.GONE
                }
                viewModel.getUserDevice(user.token).observe(this) {
                    adapter.submitData(lifecycle, it)
                }
            }

            private fun swipeToRefresh() {
                binding?.swipeRefresh?.setOnRefreshListener { adapter.refresh() }
            }

            override fun onResume() {
                super.onResume()
                adapter()
            }

            override fun onDestroy() {
                super.onDestroy()
                _binding = null
            }

            companion object {
            const val EXTRA_USER = "user"
            }

    private fun setViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelUserFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]
    }
        }