package com.example.projet_mobile_2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.projet_mobile_2.Data.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.json.JSONObject

class ProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setHeaderTitle("Compte")
        showBack()

        // ┌────────────────────────────────────┐
        // │          EditText                  │
        // └────────────────────────────────────┘
        val etFirstNameModified: EditText = findViewById<EditText>(R.id.et_first_name_modified)
        val etLastNameModified: EditText = findViewById<EditText>(R.id.et_last_name_modified)
        val etMailModified: EditText = findViewById<EditText>(R.id.et_e_mail_modified)
        val etAddressModified: EditText = findViewById<EditText>(R.id.et_address_modified)
        val etZipCodeModified: EditText = findViewById<EditText>(R.id.et_zip_code_modified)
        val etCityModified: EditText = findViewById<EditText>(R.id.et_city_modified)

        // ┌────────────────────────────────────┐
        // │          Button                    │
        // └────────────────────────────────────┘
        val btnProfileModify: Button = findViewById<Button>(R.id.btn_profile_modify)

        // ┌──────────────────────────────────────────┐
        // │          Database Check                  │
        // └──────────────────────────────────────────┘
        val query = setUserData("Users").limitToFirst(1)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val firstUser = dataSnapshot.children.firstOrNull()?.getValue(User::class.java)
                if (firstUser != null) {

                    val jsonObject = firstUser?.let { userToJson(it) }

                    var firstName = jsonObject!!.getString("firstName")
                    var lastName = jsonObject.getString("lastName")
                    var email = jsonObject.getString("email")
                    var address = jsonObject.getString("address")
                    var zipcode = jsonObject.getString("zipcode")
                    var city = jsonObject.getString("city")

                    // ┌────────────────────────────────────────────────┐
                    // │          Set Data to EditText                  │
                    // └────────────────────────────────────────────────┘
                    etFirstNameModified.setText(firstName)
                    etLastNameModified.setText(lastName)
                    etMailModified.setText(email)
                    etAddressModified.setText(address)
                    etZipCodeModified.setText(zipcode)
                    etCityModified.setText(city)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })


        // ┌───────────────────────────────────────────────┐
        // │          Data Modification                    │
        // └───────────────────────────────────────────────┘
        btnProfileModify.setOnClickListener {
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val firstUserSnapshot = dataSnapshot.children.firstOrNull()
                    if (firstUserSnapshot != null) {
                        val firstUserKey = firstUserSnapshot.key
                        val firstUser = firstUserSnapshot.getValue(User::class.java)

                        val modifiedUser = User(
                            firstName = etFirstNameModified.text.toString(),
                            lastName = etLastNameModified.text.toString(),
                            email = etMailModified.text.toString(),
                            address = etAddressModified.text.toString(),
                            zipcode = etZipCodeModified.text.toString(),
                            city = etCityModified.text.toString(),
                            cardRef = firstUser!!.cardRef
                        )

                        val userRef =
                            setUserData("Users").child(firstUserKey!!)                        // Call the setValue() method on the reference with the updated User object to update the values in the Firebase Realtime Database
                        userRef.setValue(modifiedUser).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    applicationContext,
                                    "User data updated successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "Failed to update user data",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        val intent = Intent(application, BarcodeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException())
                }
            })
        }
    }

    // ┌─────────────────────────────────────────────────────┐
    // │          CUSTOM : USER to Json Object               │
    // └─────────────────────────────────────────────────────┘
    fun userToJson(user: User): JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("firstName", user.firstName)
        jsonObject.put("lastName", user.lastName)
        jsonObject.put("email", user.email)
        jsonObject.put("address", user.address)
        jsonObject.put("zipcode", user.zipcode)
        jsonObject.put("city", user.city)
        jsonObject.put("cardRef", user.cardRef)

        return jsonObject
    }
}