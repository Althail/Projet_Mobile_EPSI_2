package com.example.projet_mobile_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


class BarcodeActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        // Écouter les changements d'onglet
        bottomNavigationView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_accueil -> {
                    // Fragment d'accueil
                    true
                }
                R.id.action_recherche -> {
                    // Fragment de recherche
                    true
                }
                R.id.action_profil -> {
                    // Fragment de profil
                    true
                }
                else -> false
            }
        })
    }
}