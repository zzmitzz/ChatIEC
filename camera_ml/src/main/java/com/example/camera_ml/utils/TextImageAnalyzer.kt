package com.example.camera_ml.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.YuvImage
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProcessingUtil
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import java.io.ByteArrayOutputStream


@OptIn(ExperimentalGetImage::class)
fun ImageProxy.toBitmap(): Bitmap? {
    val image = this.image ?: return null

    return when (image.format) {
        // Handle JPEG format
        ImageFormat.JPEG -> {
            val buffer = image.planes[0].buffer
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        }

        // Handle YUV_420_888 format
        ImageFormat.YUV_420_888 -> {
            val yBuffer = image.planes[0].buffer
            val uBuffer = image.planes[1].buffer
            val vBuffer = image.planes[2].buffer

            val ySize = yBuffer.remaining()
            val uSize = uBuffer.remaining()
            val vSize = vBuffer.remaining()

            val nv21 = ByteArray(ySize + uSize + vSize)

            // Copy Y plane
            yBuffer.get(nv21, 0, ySize)

            // Copy UV planes
            vBuffer.get(nv21, ySize, vSize)
            uBuffer.get(nv21, ySize + vSize, uSize)

            val yuvImage = YuvImage(
                nv21,
                ImageFormat.NV21,
                image.width,
                image.height,
                null
            )

            val out = ByteArrayOutputStream()
            yuvImage.compressToJpeg(
                Rect(0, 0, image.width, image.height),
                100,
                out
            )

            val imageBytes = out.toByteArray()
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            // Rotate bitmap if needed
            val matrix = Matrix()
            matrix.postRotate(this.imageInfo.rotationDegrees.toFloat())
            Bitmap.createBitmap(
                bitmap,
                0,
                0,
                bitmap.width,
                bitmap.height,
                matrix,
                true
            )
        }

        else -> null
    }
}


class TextImageAnalyzer: ImageAnalysis.Analyzer {

    public interface TextImageAnalyzerListener {
        fun onImageReady(image: Bitmap): Unit
        fun onImageProxyRead(text: Text?): Unit
    }


    val recognize = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    private var listener: TextImageAnalyzerListener? = null

    fun setListener(listener: TextImageAnalyzerListener) {
        this.listener = listener
    }

    fun unRegisterListener() {
        listener = null
    }

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy){
//        image.toBitmap()?.let {
//            listener?.onImageReady(it)
//        }
        val mediaImage = image.image
        mediaImage?.let {
            val inputImage = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)
            recognize.process(inputImage)
                .addOnSuccessListener {
                    listener?.onImageProxyRead( it )
                    image.close()
                }
                .addOnFailureListener{
                    image.close()
                }

        }

    }
}