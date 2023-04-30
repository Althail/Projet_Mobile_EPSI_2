package com.example.projet_mobile_2.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.projet_mobile_2.Data.Offres
import com.example.projet_mobile_2.R
import com.squareup.picasso.Picasso

class OffresAdapter(val offres: ArrayList<Offres>) :
    RecyclerView.Adapter<OffresAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById<TextView>(R.id.textViewName)
        val pictureViewOffres: ImageView = view.findViewById<ImageView>(R.id.pictureViewOffres)
        val descriptionViewOffres: TextView =
            view.findViewById<TextView>(R.id.descriptionViewOffres)
        val layoutContent: LinearLayout = view.findViewById<LinearLayout>(R.id.layoutContent)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cell_offres, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val offres = offres[position]
        holder.textViewName.text = offres.name
        holder.descriptionViewOffres.text = offres.description
        Picasso.get().load(offres.picture_url).into(holder.pictureViewOffres)
        holder.layoutContent.setOnClickListener(View.OnClickListener {
            Toast.makeText(holder.layoutContent.context, offres.name, Toast.LENGTH_SHORT).show()
        })
    }

    override fun getItemCount(): Int {
        return offres.size
    }
}
