package com.example.projet_mobile_2

import android.os.Bundle
import android.os.Looper
import android.os.Handler
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // ┌──────────────────────────────────────────┐
        // │          Database Check                  │
        // └──────────────────────────────────────────┘
        val database = FirebaseDatabase.getInstance(getString(R.string.db_url)).reference
        val query = database.child("Users")

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    Handler(Looper.getMainLooper()).postDelayed(Runnable{
                        val newIntent1 = Intent(application, BarcodeActivity::class.java)
                        startActivity(newIntent1)
                        finish()
                    },2000)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed(Runnable{
                        val newIntent2 = Intent(application, MainActivity::class.java)
                        startActivity(newIntent2)
                        finish()
                    },2000)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error")
            }
        })




    }
}