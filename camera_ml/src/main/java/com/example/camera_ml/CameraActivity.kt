package com.example.camera_ml

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.camera_ml.databinding.ActivityCameraBinding
import com.example.camera_ml.utils.RationalDialog

class CameraActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCameraBinding.inflate(layoutInflater)
    }
    private lateinit var cameraProviderFuture: ProcessCameraProvider
    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){ permissions ->
        if(permissions.entries.all { it.key in REQUIRED_PERMISSION && !it.value }){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSION[0])){
                val rationalDialog = RationalDialog.Builder(
                    this,
                    "Camera Permission",
                    "Camera permission is required to use camera, please go to setting to allow camera permission",
                    object : RationalDialog.DialogListener{
                        override fun onPositiveButtonClick() {
                            navigateToSettingPermission()
                        }
                        override fun onNegativeButtonClick() {
                            Toast.makeText(this@CameraActivity, "Camera permission is required to use this function", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                )
                rationalDialog?.show()
            }else{
                requestPermissions()
            }
        }else{
            setUpCamera()
        }
    }
    private val cameraListener = Runnable {

    }
    fun navigateToSettingPermission(){
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if(!checkPermissionsGranted()){
            requestPermissions()
        }
        setupView()
    }

    private fun checkPermissionsGranted(): Boolean{
        return REQUIRED_PERMISSION.all {
            ContextCompat.checkSelfPermission(
                this, it) == PackageManager.PERMISSION_GRANTED
        }
    }
    private fun setupView(){


    }
    private fun setUpCamera(){
        val cameraProvideFuture = ProcessCameraProvider.getInstance(this)

    }


    private fun requestPermissions(){
        activityResultLauncher.launch(REQUIRED_PERMISSION.toTypedArray())
    }

    companion object{
        private val REQUIRED_PERMISSION = mutableListOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        ).apply {
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.P){
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }
}