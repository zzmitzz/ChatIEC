package com.example.camera

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


interface QRReader {
    fun startScanCode(): Flow<QRResult>
    fun closeScanCode(): Unit
}

sealed class QRResult {
    data class Success(val result: String) : QRResult()
    data object Canceled : QRResult()
    data class Failed(val errorMessage: String) : QRResult()
}


class QRReaderML @Inject constructor(
    @ApplicationContext context: Context,
) : QRReader{

    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
        .enableAutoZoom()
        .build()

    private var scanner: GmsBarcodeScanner? = GmsBarcodeScanning.getClient(context, options)



    override fun startScanCode() = callbackFlow<QRResult> {
        scanner?.let {
            it.startScan()
                .addOnSuccessListener { barcode ->
                    trySend(QRResult.Success(barcode.rawValue ?: "Nothing found"))
                }
                .addOnCanceledListener {
                    trySend(QRResult.Canceled)
                }
                .addOnFailureListener{
                    trySend(QRResult.Failed("Something went wrong"))
                }
        }
        awaitClose {
            closeScanCode()
        }
    }

    override fun closeScanCode(): Unit {
        scanner = null
    }
}