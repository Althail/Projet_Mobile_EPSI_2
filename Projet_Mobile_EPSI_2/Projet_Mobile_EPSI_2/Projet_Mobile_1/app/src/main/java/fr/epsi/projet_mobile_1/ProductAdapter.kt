package fr.epsi.projet_mobile_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ProductAdapter (val products: ArrayList<Product>): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener : onItemClickListener) {

        mListener = listener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cell_product, viewGroup, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products.get(position)
        holder.textViewProductDesc.text = product.description
        holder.textViewProductName.text = product.name
        Picasso.get().load(product.pictureUrl).into(holder.imageProduct)
    }

    class ViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view){
        val textViewProductName = view.findViewById<TextView>(R.id.productName)
        val textViewProductDesc = view.findViewById<TextView>(R.id.productDesc)
        val imageProduct = view.findViewById<ImageView>(R.id.imageProduct)
        val layoutProductContent= view.findViewById<LinearLayout>(R.id.layoutProductContent)


        init {
            layoutProductContent.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }


    override fun getItemCount(): Int {
        return products.size
    }
}