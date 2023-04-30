package com.example.projet_mobile_2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // ┌──────────────────────────────────────────┐
        // │          Database Check                  │
        // └──────────────────────────────────────────┘
        val query = setUserData("Users")

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        val newIntent1 = Intent(application, BarcodeActivity::class.java)
                        startActivity(newIntent1)
                        finish()
                    }, 2000)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        val newIntent2 = Intent(application, MainActivity::class.java)
                        startActivity(newIntent2)
                        finish()
                    }, 2000)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error")
            }
        })


    }
}