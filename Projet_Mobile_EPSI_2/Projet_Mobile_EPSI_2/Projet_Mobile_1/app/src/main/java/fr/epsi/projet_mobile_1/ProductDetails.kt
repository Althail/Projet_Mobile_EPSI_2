package fr.epsi.projet_mobile_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ProductDetails : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        showBack()

        val bundle : Bundle?= intent.extras
        val name = bundle!!.getString("name")
        val desc = bundle.getString("description")
        val pictureUrl = bundle.getString("picture_url")

        setHeaderTitle(name)

        val imagePicture = findViewById<ImageView>(R.id.imageViewProduct)
        if(imagePicture != null) {
            Picasso.get().load(pictureUrl).into(imagePicture)
        }
        val descGlobal = findViewById<TextView>(R.id.productDescBig)
        descGlobal.text = desc

    }
}