package fr.epsi.projet_mobile_1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import okio.IOException
import org.json.JSONObject

class ProductsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rayon_products)
        showBack()

        val bundle : Bundle?= intent.extras
        val title = bundle!!.getString("title")
        val productUrl = bundle!!.getString("products_url")

        Log.i("Epsi G2", "################ productUrl :$productUrl")
        Log.i("Epsi G2", "################ productTitle :$title")

        setHeaderTitle(title)



        val products = arrayListOf<Product>()

        val recyclerViewProduct = findViewById<RecyclerView>(R.id.recyclerviewProductList)
        recyclerViewProduct.layoutManager = LinearLayoutManager(this)
        val productAdapter = ProductAdapter(products)
        recyclerViewProduct.adapter = productAdapter

        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
        val request = productUrl?.let {
            Request.Builder()
                .url(it)
                .get()
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build()
        }


        if (request != null) {
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println(e.stackTraceToString())
                }

                override fun onResponse(call: Call, response: Response) {
                    val data = response.body?.string()

                    if (data != null) {
                        val jsRayons = JSONObject(data)
                        val jsArrayRayons = jsRayons.getJSONArray("items")
                        for (i in 0 until jsArrayRayons.length()) {
                            val js = jsArrayRayons.getJSONObject(i)
                            val product = Product(
                                js.optString("name", "not found"),
                                js.optString("description", "not found"),
                                js.optString("picture_url", "not found")
                            )
                            products.add(product)
                            runOnUiThread(Runnable {
                                productAdapter.notifyDataSetChanged()
                            })
                        }
                        productAdapter.setOnItemClickListener(object : ProductAdapter.onItemClickListener {
                            override fun onItemClick(position: Int) {

                                val intent2 = Intent(this@ProductsActivity, ProductDetails::class.java)
                                intent2.putExtra("name", products[position].name)
                                intent2.putExtra("description", products[position].description)
                                intent2.putExtra("picture_url", products[position].pictureUrl)
                                startActivity(intent2)
                            }
                        })
                    }
                }
            })
        }
    }
}