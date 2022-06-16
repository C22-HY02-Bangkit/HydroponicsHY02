package com.capstone.hidroponichy02.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.capstone.hidroponichy02.R
import com.capstone.hidroponichy02.data.Socket
import com.capstone.hidroponichy02.databinding.ActivityControlBinding
import com.capstone.hidroponichy02.databinding.ActivityDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    var ph=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra(EXTRA_NAME)
        binding.idDevice.text=id
        Socket.setSocket()
        Socket.establishConnection()

        val mSocket = Socket.getSocket()

            mSocket.on("realtime") { args ->
                val counter = args[0] as JSONObject
                val sensor = counter.getJSONObject("sensor_data")
                val ph = sensor.getString("ph")
                val tds = sensor.getString("tds")
                val ec = sensor.getString("ec")

                Log.i("ph",ph)
                Log.i("tds",tds)
                Log.i("ec",ec)
                runOnUiThread {
                    binding.phSensor.text = ph
                    binding.tdsCensor.text = tds
                    binding.ecSensor.text = ec
                }
            }
        val navView: BottomNavigationView = binding.bottom
        navView.setSelectedItemId(R.id.sensor)
        navView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.control ->{
                    startActivity(Intent(applicationContext, ControlActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.sensor -> return@OnNavigationItemSelectedListener true
            }
            false
        })
        binding.back.setOnClickListener{
            navigateUpTo(Intent(this@DetailActivity, MainActivity::class.java))
            true
        }

    }
    companion object {
        const val EXTRA_ID = "id"
        const val EXTRA_NAME = "name"

    }
}