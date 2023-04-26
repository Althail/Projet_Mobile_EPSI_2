package com.example.projet_mobile_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


class BarcodeActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        // Écouter les changements d'onglet
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            var selectedFragment: Fragment? = null
            when (menuItem.itemId) {
                R.id.action_accueil -> {
                    val carteFragment = CarteFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, carteFragment)
                        .commit()
                    true
                }
                R.id.action_recherche -> {
                    val offresFragment = OffresFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, offresFragment)
                        .commit()
                    true
                }
                R.id.action_profil -> {
                    val magasinsFragment = MagasinsFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, magasinsFragment)
                        .commit()
                    true
                }
                else -> false
            }
        }

        // Afficher le fragment d'accueil par défaut
        val defaultFragment = CarteFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, defaultFragment)
            .commit()
    }
}
