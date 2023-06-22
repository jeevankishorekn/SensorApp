package com.jeevan.lightsensor

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.jeevan.lightsensor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var binding: ActivityMainBinding
    private var mLight: Sensor? = null
    private lateinit var lightSensor: SensorManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lightSensor = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mLight = lightSensor.getDefaultSensor(Sensor.TYPE_LIGHT)
        Log.d(TAG, "onCreate: ${lightSensor.getSensorList(Sensor.TYPE_ALL).map { it.name } }}")
        binding.lightProgressBar.max = 40000

    }

    override fun onResume() {
        super.onResume()
        lightSensor.registerListener(this,mLight,SensorManager.SENSOR_DELAY_FASTEST)

    }

    override fun onPause() {
        super.onPause()
        lightSensor.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            Log.d(TAG, "onSensorChanged: ${event.values[0]}")
            val brightness = event.values[0]
            binding.lightProgressBar.progress = brightness.toInt()
            binding.lightAmt.text = brightness.toString()
            when(brightness.toInt()){
                in 0 .. 5 -> binding.parentScreen.background = ContextCompat.getDrawable(this,R.color.black)
                in 5 .. 10 -> binding.parentScreen.background = ContextCompat.getDrawable(this,R.color.purple_200)
                in 10.. 30 -> binding.parentScreen.background = ContextCompat.getDrawable(this,R.color.purple_500)
                in 30 .. 50 -> binding.parentScreen.background = ContextCompat.getDrawable(this,R.color.purple_700)
                in 50 .. 100 -> binding.parentScreen.background = ContextCompat.getDrawable(this,R.color.teal_200)
                in 100 .. 300 -> binding.parentScreen.background = ContextCompat.getDrawable(this,R.color.teal_700)
                in 300 .. 500 -> binding.parentScreen.background = ContextCompat.getDrawable(this,R.color.white100)
                in 500 .. 1500 -> binding.parentScreen.background = ContextCompat.getDrawable(this,R.color.white)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}
