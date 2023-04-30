package com.example.projet_mobile_2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projet_mobile_2.databinding.ActivityInscriptionBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InscriptionActivity : BaseActivity() {

    private lateinit var binding: ActivityInscriptionBinding
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent

        // Inscription EditText
        val etFirstName: EditText = findViewById<EditText>(R.id.et_first_name)
        val etLastName: EditText = findViewById<EditText>(R.id.et_last_name)
        val etMail: EditText = findViewById<EditText>(R.id.et_e_mail)
        val etAddress: EditText = findViewById<EditText>(R.id.et_address)
        val etZipCode: EditText = findViewById<EditText>(R.id.et_zip_code)
        val etCity: EditText = findViewById<EditText>(R.id.et_city)
        val etCardRef: EditText = findViewById<EditText>(R.id.et_cardRef)

        if (intent != null) {
            // Data from QR Code
            val firstName = intent.getStringExtra("firstName")
            val lastName = intent.getStringExtra("lastName")
            val email = intent.getStringExtra("email")
            val address = intent.getStringExtra("address")
            val zipcode = intent.getStringExtra("zipcode")
            val city = intent.getStringExtra("city")
            val cardRef = intent.getStringExtra("cardRef")

            // SetText
            etFirstName.setText(firstName)
            etLastName.setText(lastName)
            etMail.setText(email)
            etAddress.setText(address)
            etZipCode.setText(zipcode)
            etCity.setText(city)
            etCardRef.setText(cardRef)
        }

        val btnRegister: Button = findViewById<Button>(R.id.btn_register)
        btnRegister.setOnClickListener(View.OnClickListener() {
            val inputFirstName = etFirstName.text.toString()
            val inputLastName = etLastName.text.toString()
            val inputEmail = etMail.text.toString()
            val inputAddress = etAddress.text.toString()
            val inputZipCode = etZipCode.text.toString()
            val inputCity = etCity.text.toString()
            val inputCardRef = etCardRef.text.toString()

            database = setUserData("Users")
            val user = User(
                inputFirstName,
                inputLastName,
                inputEmail,
                inputAddress,
                inputZipCode,
                inputCity,
                inputCardRef
            )
            database.child("Users").setValue(user).addOnSuccessListener {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show() }

            var intentBarcode = Intent(application, BarcodeActivity::class.java);
            intentBarcode.putExtra("name", "$inputFirstName $inputLastName")
            startActivity(intentBarcode)
        })
    }
}