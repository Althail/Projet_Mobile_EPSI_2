package com.example.projet_mobile_2

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.CodeScanner
import android.Manifest
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback

class QrScanActivity: AppCompatActivity() {

    lateinit var codeScanner : CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscan)

        // Check Authentication
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                123)
        } else {
            scan()
        }

        // Reset Button
        val btn_reset:Button = findViewById(R.id.btn_scan_reset)
        btn_reset.setOnClickListener{
            if(::codeScanner.isInitialized){
                codeScanner.startPreview()
            }
        }
    }

    private fun scan() {
        val scannerView: CodeScannerView = findViewById(R.id.scanner_view)

        val codeText: TextView = findViewById<TextView>(R.id.scanned_text)

        codeScanner = CodeScanner(this,scannerView)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            isAutoFocusEnabled = true
            isFlashEnabled = false

            // Show Text From Scan
            decodeCallback = DecodeCallback{
                runOnUiThread {

                    codeText.text = it.text
                }
            }

            // Error
            codeScanner.errorCallback = ErrorCallback {
                runOnUiThread {
                    codeText.text = "Scan Error ${it.message}"
                }
            }

            // ScanView Click Event
            scannerView.setOnClickListener {
                if (::codeScanner.isInitialized) {
                    codeScanner.startPreview()
                }
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode ==123){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext, "Camera Authorized", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "Camera Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(::codeScanner.isInitialized){
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        super.onPause()
        if(::codeScanner.isInitialized){
            codeScanner.releaseResources()
        }
    }
}