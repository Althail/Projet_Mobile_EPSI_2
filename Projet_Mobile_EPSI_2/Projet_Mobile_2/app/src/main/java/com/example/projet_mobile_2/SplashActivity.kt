package com.example.projet_mobile_2

import android.os.Bundle
import android.os.Looper
import android.os.Handler
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity


class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed(Runnable{
            val newIntent = Intent(application, MainActivity::class.java)
            startActivity(newIntent)
            finish()
        },2000)
    }
}