package com.example.projet_mobile_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.json.JSONObject
import kotlin.reflect.typeOf

class ProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setHeaderTitle("Compte")
        showBack()

        // ┌──────────────────────────────────────────┐
        // │          Database Check                  │
        // └──────────────────────────────────────────┘
        val et_first_name_modified:EditText = findViewById<EditText>(R.id.et_first_name_modified)
        val et_last_name_modified:EditText = findViewById<EditText>(R.id.et_last_name_modified)
        val et_e_mail_modified:EditText = findViewById<EditText>(R.id.et_e_mail_modified)
        val et_address_modified:EditText = findViewById<EditText>(R.id.et_address_modified)
        val et_zip_code_modified:EditText = findViewById<EditText>(R.id.et_zip_code_modified)
        val et_city_modified:EditText = findViewById<EditText>(R.id.et_city_modified)

        val database = FirebaseDatabase.getInstance(getString(R.string.db_url)).reference
        val query = database.child("Users").limitToFirst(1)

        val btn_profile_modify:Button = findViewById<Button>(R.id.btn_profile_modify)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This will be called once with the initial value and again
                // whenever data at this location is updated.
                val firstUser = dataSnapshot.children.firstOrNull()?.getValue(User::class.java)
                if (firstUser != null) {

                    // Get the User object from the first child

                    val jsonObject = firstUser?.let { userToJson(it) }

                    var firstName = jsonObject!!.getString("firstName")
                    var lastName = jsonObject.getString("lastName")
                    var email = jsonObject.getString("email")
                    var address = jsonObject.getString("address")
                    var zipcode = jsonObject.getString("zipcode")
                    var city = jsonObject.getString("city")

                    et_first_name_modified.setText(firstName)
                    et_last_name_modified.setText(lastName)
                    et_e_mail_modified.setText(email)
                    et_address_modified.setText(address)
                    et_zip_code_modified.setText(zipcode)
                    et_city_modified.setText(city)


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
        btn_profile_modify.setOnClickListener{
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This will be called once with the initial value and again
                    // whenever data at this location is updated.
                    val firstUserSnapshot = dataSnapshot.children.firstOrNull()
                    if (firstUserSnapshot != null) {
                        // Get the User object from the first child
                        val firstUserKey = firstUserSnapshot.key
                        val firstUser = firstUserSnapshot.getValue(User::class.java)

                        val modifiedUser = User(
                            firstName = et_first_name_modified.text.toString(),
                            lastName = et_last_name_modified.text.toString(),
                            email = et_e_mail_modified.text.toString(),
                            address = et_address_modified.text.toString(),
                            zipcode = et_zip_code_modified.text.toString(),
                            city = et_city_modified.text.toString(),
                            cardRef = firstUser!!.cardRef
                        )


                        // Get a reference to the location of the User object in the Firebase Realtime Database
                        val database = FirebaseDatabase.getInstance().reference
                        val userRef = database.child("Users").child(firstUserKey!!)                        // Call the setValue() method on the reference with the updated User object to update the values in the Firebase Realtime Database
                        userRef.setValue(modifiedUser).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(applicationContext, "User data updated successfully", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(applicationContext, "Failed to update user data", Toast.LENGTH_SHORT).show()
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