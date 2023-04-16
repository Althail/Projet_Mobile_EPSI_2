package com.example.projet_mobile_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ┌──────────────────────────────────────────┐
        // │          Button - Inscription            │
        // └──────────────────────────────────────────┘
        var btn_inscription: Button = findViewById<Button>(R.id.btn_inscription)
        btn_inscription.setOnClickListener(View.OnClickListener (){
            var intent_inscription = Intent(application, InscriptionActivity::class.java);
            startActivity(intent_inscription)
        })
    }
}