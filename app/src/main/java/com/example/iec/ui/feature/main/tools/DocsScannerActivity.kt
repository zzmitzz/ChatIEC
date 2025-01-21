package com.example.iec.ui.feature.main.tools

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions.SCANNER_MODE_FULL
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult


class DocsScannerActivity : ComponentActivity() {


    private val options = GmsDocumentScannerOptions.Builder()
        .setPageLimit(3)
        .setGalleryImportAllowed(true)
        .setScannerMode(SCANNER_MODE_FULL)
        .setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_PDF)
        .build()

    private val scanner = GmsDocumentScanning.getClient(options)

    private val scannerLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
            result ->
        run {
            if (result.resultCode == RESULT_OK) {
                val ans =
                    GmsDocumentScanningResult.fromActivityResultIntent(result.data)
                ans?.pages?.let { pages ->
                    for (page in pages) {
                        val imageUri = pages[0].imageUri
                    }
                }
                ans?.pdf?.let { pdf ->
                    val pdfUri = pdf.uri
                    val pageCount = pdf.pageCount
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        scanner.getStartScanIntent(this)
            .addOnSuccessListener { intentSender ->
                scannerLauncher.launch(IntentSenderRequest.Builder(intentSender).build())
            }
            .addOnFailureListener {
            }
    }

}