package com.capstone.hidroponichy02.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.capstone.hidroponichy02.R
import com.capstone.hidroponichy02.data.Socket
import com.capstone.hidroponichy02.databinding.ActivityControlBinding
import com.capstone.hidroponichy02.databinding.ActivityDetailBinding
import com.capstone.hidroponichy02.ml.Model1
import com.capstone.hidroponichy02.model.UserModel
import com.capstone.hidroponichy02.response.DeviceResponse
import com.capstone.hidroponichy02.response.ResultResponse
import com.capstone.hidroponichy02.service.ApiConfig
import com.capstone.hidroponichy02.service.ApiConfig.Companion.getApiService
import com.capstone.hidroponichy02.viewmodel.DeviceViewModel
import com.capstone.hidroponichy02.viewmodel.RegisterViewModel
import com.capstone.hidroponichy02.viewmodel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONObject
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var user: UserModel
    private val deviceViewModel: DeviceViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    var ph=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra(EXTRA_NAME)
        binding.idDevice.text=id
        val plant = intent.getStringExtra(EXTRA_PLANT)
        binding.idPlant.text=plant
        val status = intent.getStringExtra(EXTRA_STATUS)
        binding.idStatus.text=status
        Socket.setSocket()
        Socket.establishConnection()

        val mSocket = Socket.getSocket()

            mSocket.on("realtime") { args ->
                val counter = args[0] as JSONObject
                val sensor = counter.getJSONObject("sensor_data")
                val ph = sensor.getString("ph").toFloat()
                val tds = sensor.getString("tds").toFloat()
                val ec = sensor.getString("ec").toFloat()

                Log.i("ph",ph.toString())
                Log.i("tds",tds.toString())
                Log.i("ec",ec.toString())
                runOnUiThread {
                    binding.phSensor.text = ph.toString()
                    binding.tdsCensor.text = tds.toString()
                    binding.ecSensor.text = ec.toString()
                    //OTOMATIS SENSOR
                    //b.setOnClickListener(View.OnClickListener{
                    val model = Model1.newInstance(this)

                    /* var tds1: Float = tds2.text.toString().toFloat()
                     var ph1: Float = ph2.text.toString().toFloat()
                     var tds: Float = ((tds1 - 51.0)/(1130.0 - 51.0)).toFloat()
                     var ph: Float = ((ph1 - 0)/(14.5 - 0)).toFloat()*/
                    var tds=((tds-0)/(14.5-0)).toFloat()
                    var ph=((ph - 0)/(14.5 - 0)).toFloat()
                    //var bytebuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * 2 )
                    //bytebuffer.putFloat(tds)
                    //bytebuffer.putFloat(ph)

                    // Creates inputs for reference.
                    //val inputFeature0 = TensorBufferFloat.createFixedSize(intArrayOf(1, 2), DataType.FLOAT32)
                    //inputFeature0.loadBuffer(bytebuffer)

                    val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 2), DataType.FLOAT32)
                    inputFeature0.loadArray(floatArrayOf(tds, ph))


                    // Runs model inference and gets result.
                    val outputs = model.process(inputFeature0)
                    val outputFeature0 = outputs.outputFeature0AsTensorBuffer
                    val confidences = outputFeature0.floatArray

                    var maxPos = 0
                    var maxConfidence = 0f
                    for (i in confidences.indices){
                        if (confidences[i] > maxConfidence){
                            maxConfidence = confidences[i]
                            maxPos = i
                        }
                    }

                    val classes = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
                    val result = classes[maxPos]

                    binding.idStatus.text=result.toString()

                    // Releases model resources if no longer used.
                   // model.close()
                    //})
                    val id = intent.getStringExtra(EXTRA_ID).toString()
                    val status = result.toInt()
                    deviceViewModel.update(id, status).observe(this) {
                        when (it) {
                            is ResultResponse.Loading -> {
                                //
                            }
                            is ResultResponse.Success -> {
                                //
                            }
                            is ResultResponse.Error -> {
                                binding.idPlant.text = "GAGAL MANING"
                            }
                        }
                    }
                }
            }
        //untuk buat timer update jika di rasa server akan berat
/*binding.get.setOnClickListener {
    //val token = "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImJhZjU5N2YwLTQ5MzgtNDVmNi1iZDU2LTFmMGUwOWM1MWRmMiIsImlhdCI6MTY1NDkxNjYyNiwiZXhwIjoxNjU3NTA4NjI2fQ.4ihtZZxwOgB2UVlLFeZRMkuh5toTXLeTGztN5vCxdMI"
    val id = intent.getStringExtra(EXTRA_ID).toString()
    val status = 3
    deviceViewModel.update(id, status).observe(this) {
        when (it) {
            is ResultResponse.Loading -> {
                //
            }
            is ResultResponse.Success -> {
                //
            }
            is ResultResponse.Error -> {
                binding.idPlant.text = "GAGAL MANING"
            }
        }
    }
}*/
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
        const val EXTRA_PLANT = "plant"
        const val EXTRA_STATUS = ""
        const val EXTRA_TOKEN = "token"

    }
}