package com.example.projet_mobile_2

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class CarteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_carte, container, false)
        val query = setUserData("Users").limitToFirst(1)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This will be called once with the initial value and again
                // whenever data at this location is updated.
                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    val jsonObject = user?.let { userToJson(it) }

                    val firstName = jsonObject!!.getString("firstName")
                    val lastName = jsonObject!!.getString("lastName")
                    val cardRef = jsonObject!!.getString("cardRef")

                    val barcode_owner_name: TextView = view.findViewById(R.id.barcode_owner_name)
                    val barcode_ref_number: TextView = view.findViewById(R.id.barcode_ref_number)
                    // Set the text of the TextView
                    barcode_owner_name.setText(firstName + " " + lastName)
                    barcode_ref_number.setText(cardRef)
                    generateBarcode(cardRef ?: "0123456789")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", databaseError.toException())
            }
        })
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CarteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
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

    fun setUserData(pathString: String): DatabaseReference {
        val database = FirebaseDatabase.getInstance(getString(R.string.db_url)).reference

        return database.child(pathString)
    }

    private fun generateBarcode(refNum: String) {
        val imageView: ImageView = requireView().findViewById<ImageView>(R.id.image_barcode)
        val bitmap = encodeBitmap(refNum, BarcodeFormat.CODE_128, 800, 300)
        imageView.setImageBitmap(bitmap)
    }

    @Throws(WriterException::class)
    private fun encodeBitmap(
        contents: String,
        format: BarcodeFormat,
        imageWidth: Int,
        imageHeight: Int
    ): Bitmap {
        val writer = MultiFormatWriter()
        val bitMatrix: BitMatrix = writer.encode(contents, format, imageWidth, imageHeight)
        val pixels = IntArray(imageWidth * imageHeight)
        for (y in 0 until imageHeight) {
            val offset = y * imageWidth
            for (x in 0 until imageWidth) {
                pixels[offset + x] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(imageWidth, imageHeight, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, imageWidth, 0, 0, imageWidth, imageHeight)
        return bitmap
    }
}