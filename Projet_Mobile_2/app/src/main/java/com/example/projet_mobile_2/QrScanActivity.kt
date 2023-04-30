package com.example.projet_mobile_2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import org.json.JSONObject

class QrScanActivity : AppCompatActivity() {

    lateinit var codeScanner: CodeScanner
    var scannedData: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscan)

        // Check Authentication
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                123
            )
        } else {
            scan()
        }

        // Reset Button
        val btn_reset: Button = findViewById(R.id.btn_scan_reset)
        btn_reset.setOnClickListener {
            if (::codeScanner.isInitialized) {
                codeScanner.startPreview()
            }
        }

        // Send Button
        val btn_send: Button = findViewById(R.id.btn_send_scan_data)

        btn_send.setOnClickListener {
            val jsonObject = JSONObject(scannedData)

            val firstName = jsonObject.getString("firstName")
            val lastName = jsonObject.getString("lastName")
            val email = jsonObject.getString("email")
            val address = jsonObject.getString("address")
            val zipcode = jsonObject.getString("zipcode")
            val city = jsonObject.getString("city")
            val cardRef = jsonObject.getString("cardRef")

            val newIntent = Intent(application, InscriptionActivity::class.java);
            newIntent.putExtra("firstName", firstName)
            newIntent.putExtra("lastName", lastName)
            newIntent.putExtra("email", email)
            newIntent.putExtra("address", address)
            newIntent.putExtra("zipcode", zipcode)
            newIntent.putExtra("city", city)
            newIntent.putExtra("cardRef", cardRef)

            startActivity(newIntent)
        }
    }

    private fun scan() {
        val scannerView: CodeScannerView = findViewById(R.id.scanner_view)

        val codeText: TextView = findViewById<TextView>(R.id.scanned_text)

        codeScanner = CodeScanner(this, scannerView)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            isAutoFocusEnabled = true
            isFlashEnabled = false

            // Show Text From Scan
            decodeCallback = DecodeCallback {
                runOnUiThread {

                    codeText.text = it.text
                    scannedData = it.text
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
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Camera Authorized", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Camera Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized) {
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        super.onPause()
        if (::codeScanner.isInitialized) {
            codeScanner.releaseResources()
        }
    }
}