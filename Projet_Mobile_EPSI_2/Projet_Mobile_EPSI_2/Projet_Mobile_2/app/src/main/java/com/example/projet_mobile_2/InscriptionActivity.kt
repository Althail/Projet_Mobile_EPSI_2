package com.example.projet_mobile_2

import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class InscriptionActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)

        val intent = getIntent()

        // Inscription EditText
        val et_first_name:EditText = findViewById<EditText>(R.id.et_first_name)
        val et_last_name:EditText = findViewById<EditText>(R.id.et_last_name)
        val et_mail:EditText = findViewById<EditText>(R.id.et_e_mail)
        val et_address:EditText = findViewById<EditText>(R.id.et_address)
        val et_zipcode:EditText = findViewById<EditText>(R.id.et_zip_code)
        val et_city:EditText = findViewById<EditText>(R.id.et_city)
        val et_cardRef:EditText = findViewById<EditText>(R.id.et_cardRef)

        if (intent !=null) {
            // Data from QR Code
            val firstName = intent.getStringExtra("firstName")
            val lastName = intent.getStringExtra("lastName")
            val email = intent.getStringExtra("email")
            val address = intent.getStringExtra("address")
            val zipcode = intent.getStringExtra("zipcode")
            val city = intent.getStringExtra("city")
            val cardRef = intent.getStringExtra("cardRef")

            // SetText
            et_first_name.setText(firstName)
            et_last_name.setText(lastName)
            et_mail.setText(email)
            et_address.setText(address)
            et_zipcode.setText(zipcode)
            et_city.setText(city)
            et_cardRef.setText(cardRef)
        }

        val btn_register: Button = findViewById<Button>(R.id.btn_register)
        btn_register.setOnClickListener(View.OnClickListener(){
            val input_firstName = et_first_name.getText().toString()
            val input_lastName = et_last_name.getText().toString()
            val input_email = et_mail.getText().toString()
            val input_address = et_address.getText().toString()
            val input_zipcode = et_zipcode.getText().toString()
            val input_city = et_city.getText().toString()
            val input_cardRef = et_cardRef.getText().toString()

            if (TextUtils.isEmpty(input_firstName)){
                Toast.makeText(this, "Entrez Votre Nom", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(input_lastName)){
                Toast.makeText(this, "Entrez Votre Prénom", Toast.LENGTH_SHORT).show();
            }


        })
    }
}