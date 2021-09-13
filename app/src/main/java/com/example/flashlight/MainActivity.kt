package com.example.flashlight

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private val viewModel : MainViewModel by viewModels()
    var flashAvailable by Delegates.notNull<Boolean>()
    lateinit var cameraManager:CameraManager
    lateinit var flashImage:ImageView
    lateinit var text : TextView
    lateinit var cameraId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraManager= getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraId = cameraManager.cameraIdList[0]
        flashAvailable = applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
        supportActionBar?.hide()
        flashImage = findViewById(R.id.imageView)
        text = findViewById(R.id.tv_mainText)

        flashImage.setOnClickListener {
            changeFlashlightState()
        }

        viewModel.flashLightState.observe(this,{ state->
            when(state){
                true->{
                    turnFlashLightOn()
                    flashImage.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_flash_off,theme))
                }
                false->turnFlashLightOff()
            }
        })

    }

    private fun changeFlashlightState(){
        viewModel.changeFlashLightState()
    }

    private fun turnFlashLightOn(){
        when{
            !flashAvailable -> Toast.makeText(this,"Sorry, your phone does not have flash support",Toast.LENGTH_LONG).show()
            else -> cameraManager.setTorchMode(cameraId,true)
        }
        text.text = resources.getString(R.string.turn_off)
    }

    private fun turnFlashLightOff(){
        flashImage.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_sharp_flash_on_24,theme))
        text.text = resources.getString(R.string.turn_on)
        cameraManager.setTorchMode(cameraId,false)
    }
}