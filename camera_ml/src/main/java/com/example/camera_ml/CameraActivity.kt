package com.example.camera_ml

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.camera_ml.adapter.TextExtractAdapter
import com.example.camera_ml.customviews.OverlayView
import com.example.camera_ml.databinding.ActivityCameraBinding
import com.example.camera_ml.utils.RationalDialog
import com.example.camera_ml.utils.TextImageAnalyzer
import com.example.camera_ml.viewmodel.CameraUseCaseVM
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.text.Text
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CameraActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCameraBinding.inflate(layoutInflater)
    }
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private val viewModel: CameraUseCaseVM by viewModels()
    private var cameraSide = CameraSelector.DEFAULT_BACK_CAMERA

    private lateinit var overlayView: OverlayView
    private lateinit var textListAdapter: TextExtractAdapter
    private lateinit var clipBoard: ClipboardManager
    private val imageAnalyzerListener = object : TextImageAnalyzer.TextImageAnalyzerListener {
        override fun onImageReady(image: Bitmap) {
        }

        override fun onImageProxyRead(text: Text?) {
            val result: MutableList<Pair<Rect?, String>> = mutableListOf()
            for (block in text?.textBlocks ?: emptyList()) {
                result.addAll(block.lines.mapNotNull {

                    Log.d("TAG", "onImageProxyRead: ${it.text} ${it.boundingBox}")
                    it.boundingBox?.let { Rect(it) } to it.text
                })
            }
            overlayView.setRectangles(result)

            textListAdapter.setData(result.map { it.second })
        }
    }


    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        Looper.getMainLooper()
        if (permissions.entries.all { it.key in REQUIRED_PERMISSION && !it.value }) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSION[0])) {
                val rationalDialog = RationalDialog.Builder(
                    this,
                    "Camera Permission",
                    "Camera permission is required to use camera, please go to setting to allow camera permission",
                    object : RationalDialog.DialogListener {
                        override fun onPositiveButtonClick() {
                            navigateToSettingPermission()
                        }

                        override fun onNegativeButtonClick() {
                            Toast.makeText(
                                this@CameraActivity,
                                "Camera permission is required to use this function",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }
                )
                rationalDialog?.show()
            } else {
                requestPermissions()
            }
        } else {
            setUpCamera()
        }
    }

    fun navigateToSettingPermission() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (!checkPermissionsGranted()) {
            requestPermissions()
        }
        setupView()
        clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    private fun checkPermissionsGranted(): Boolean {
        return REQUIRED_PERMISSION.all {
            ContextCompat.checkSelfPermission(
                this, it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun setupView() {
        if (checkPermissionsGranted()) {
            setUpCamera()
            binding.backIC.setOnClickListener {
                onBackPressed()
            }
        }
        overlayView = OverlayView(this)
        binding.root.addView(
            overlayView
        )

        textListAdapter = TextExtractAdapter().apply {
            selectToCopy = {
                clipBoard.setPrimaryClip(
                    ClipData.newPlainText(
                        "text",
                        it
                    )
                )
                Toast.makeText(this@CameraActivity, "Copied", Toast.LENGTH_SHORT).show()
            }
        }
        binding.recyclerView.apply {
            adapter = textListAdapter

        }
    }

    private val cameraListener = Runnable {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(binding.previewView.surfaceProvider)
        }
        val cameraAnalyzer = TextImageAnalyzer().apply {
            setListener(imageAnalyzerListener)
        }
        val imageAnalyzer = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also { it ->
                it.setAnalyzer(ContextCompat.getMainExecutor(this), cameraAnalyzer)
            }
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(this, cameraSide, preview, imageAnalyzer)
        } catch (e: Exception) {
            Toast.makeText(this, "Camera is not available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpCamera() {
        val cameraProvideFuture = ProcessCameraProvider.getInstance(this)
        cameraProvideFuture.addListener(cameraListener, ContextCompat.getMainExecutor(this))
    }


    private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSION.toTypedArray())
    }

    companion object {
        private val REQUIRED_PERMISSION = mutableListOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }
}