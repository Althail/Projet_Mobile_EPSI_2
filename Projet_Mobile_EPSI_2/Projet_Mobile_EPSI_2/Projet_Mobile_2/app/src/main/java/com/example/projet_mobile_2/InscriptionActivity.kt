package com.example.projet_mobile_2

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class InscriptionActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)

        val intent = getIntent()

        if (intent !=null) {
            // Data from QR Code
            val firstName = intent.getStringExtra("firstName")
            val lastName = intent.getStringExtra("lastName")
            val email = intent.getStringExtra("email")
            val address = intent.getStringExtra("address")
            val zipcode = intent.getStringExtra("zipcode")
            val city = intent.getStringExtra("city")
            val cardRef = intent.getStringExtra("cardRef")

            // Inscription EditText
            val et_first_name:EditText = findViewById<EditText>(R.id.et_first_name)
            val et_last_name:EditText = findViewById<EditText>(R.id.et_last_name)
            val et_mail:EditText = findViewById<EditText>(R.id.et_e_mail)
            val et_address:EditText = findViewById<EditText>(R.id.et_address)
            val et_zipcode:EditText = findViewById<EditText>(R.id.et_zip_code)
            val et_city:EditText = findViewById<EditText>(R.id.et_city)
            val et_cardRef:EditText = findViewById<EditText>(R.id.et_cardRef)

            // SetText
            et_first_name.setText(firstName)
            et_last_name.setText(lastName)
            et_mail.setText(email)
            et_address.setText(address)
            et_zipcode.setText(zipcode)
            et_city.setText(city)
            et_cardRef.setText(cardRef)


        }

    }
}