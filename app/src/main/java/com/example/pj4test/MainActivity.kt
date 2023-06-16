package com.example.pj4test

import android.Manifest.permission.CAMERA
import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import java.util.*
import com.example.pj4test.fragment.AudioFragment
import com.example.pj4test.fragment.CameraFragment
import com.example.pj4test.AudioFragmentListener

class MainActivity : AppCompatActivity(), AudioFragmentListener {
    private val TAG = "MainActivity"

    private lateinit var audioFragment: AudioFragment
    private lateinit var cameraFragment: CameraFragment

    // permissions
    private val permissions = arrayOf(RECORD_AUDIO, CAMERA)
    private val PERMISSIONS_REQUEST = 0x0000001;

    @RequiresApi(Build.VERSION_CODES.M)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        audioFragment = AudioFragment()
        cameraFragment = CameraFragment()

        audioFragment.setAudioFragmentListener(this)
        supportFragmentManager.beginTransaction()
            .replace(R.id.audioFragmentContainerView, audioFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .replace(R.id.cameraFragmentContainerView, cameraFragment)
            .commit()

        Log.d("MainActivity", "Hello World Hi 33333")

        checkPermissions() // check permissions
    }

    override fun onSnapDetected() {
        // Enable person detection in the CameraFragment
        cameraFragment.startPersonDetection()
        Log.d("MainActivity", "Hello World Hi 22")
    }

    private fun checkPermissions() {
        if (permissions.all{ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED}){
            Log.d(TAG, "All Permission Granted")
        }
        else{
            requestPermissions(permissions, PERMISSIONS_REQUEST)
        }
    }
}