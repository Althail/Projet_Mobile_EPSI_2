package com.example.projet_mobile_2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class BarcodeActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)
        showProfile()

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        // Écouter les changements d'onglet
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            var selectedFragment: Fragment? = null
            when (menuItem.itemId) {
                // ┌──────────────────────────────────────────┐
                // │          Fragment Carte                  │
                // └──────────────────────────────────────────┘
                R.id.action_carte -> {
                    val carteFragment = CarteFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, carteFragment)
                        .commit()
                    true
                }

                // ┌──────────────────────────────────────────┐
                // │          Fragment Offre                  │
                // └──────────────────────────────────────────┘
                R.id.action_offre -> {
                    val offresFragment = OffresFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, offresFragment)
                        .commit()
                    true
                }

                // ┌────────────────────────────────────────────┐
                // │          Fragment Magasin                  │
                // └────────────────────────────────────────────┘
                R.id.action_magasin -> {
                    val magasinsFragment = MagasinsFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, magasinsFragment)
                        .commit()
                    true
                }
                else -> false
            }
        }

        // ┌───────────────────────────────────────────────────────┐
        // │          By Default : Fragment Carte                  │
        // └───────────────────────────────────────────────────────┘
        val defaultFragment = CarteFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, defaultFragment)
            .commit()
    }

    // ┌─────────────────────────────────────────────────────────┐
    // │          CUSTOM : Show Profile Activity                 │
    // └─────────────────────────────────────────────────────────┘
    fun showProfile() {
        val imageViewProfile: ImageView = findViewById<ImageView>(R.id.btn_account)
        imageViewProfile.setOnClickListener(View.OnClickListener {
            val intent = Intent(application, ProfileActivity::class.java)
            startActivity(intent)
        })
    }
}
