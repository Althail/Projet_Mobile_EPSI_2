package com.example.projet_mobile_2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ┌──────────────────────────────────────────┐
        // │          Button - Inscription            │
        // └──────────────────────────────────────────┘
        var btnInscription: Button = findViewById<Button>(R.id.btn_inscription)
        btnInscription.setOnClickListener(View.OnClickListener() {
            var intentInscription = Intent(application, InscriptionActivity::class.java);
            startActivity(intentInscription)
        })

        // ┌──────────────────────────────────────────┐
        // │          Button - QR Code                │
        // └──────────────────────────────────────────┘
        var btnQrCode: Button = findViewById<Button>(R.id.btn_qr_code)
        btnQrCode.setOnClickListener(View.OnClickListener() {
            var intentQrCode = Intent(application, QrScanActivity::class.java)
            startActivity(intentQrCode)
        })

    }
}