package com.example.projet_mobile_2

import android.os.Bundle
import android.widget.TextView

class MagasinDetailActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magasindetail)

        val intent = getIntent()
        var title: String? = null
        var store_img_url: String? = null
        var address: String? = null
        var code_postal: String? = null
        var city: String? = null
        var description: String? = null

        if (intent !=null) {
            title = intent.getStringExtra("title")
            store_img_url = intent.getStringExtra("store_img_url")
            address = intent.getStringExtra("address")
            code_postal = intent.getStringExtra("code_postal")
            city = intent.getStringExtra("city")
            description = intent.getStringExtra("description")

        }

        setHeaderTitle(title.toString())
        showBack()

        val textViewAddress:TextView = findViewById<TextView>(R.id.text_magasin_address)
        val textViewCodePostalCity:TextView = findViewById<TextView>(R.id.text_magasin_code_postal_city)
        val textViewDescription:TextView = findViewById<TextView>(R.id.text_magasin_description)

        textViewAddress.text=address.toString()
        textViewCodePostalCity.text= code_postal.toString()+" "+city.toString()
        textViewDescription.text=description.toString()

    }
}