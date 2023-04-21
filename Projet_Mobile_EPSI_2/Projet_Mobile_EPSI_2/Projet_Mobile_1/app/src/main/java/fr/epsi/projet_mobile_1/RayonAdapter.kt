package fr.epsi.projet_mobile_1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class RayonAdapter (val rayons: ArrayList<Rayon>): RecyclerView.Adapter<RayonAdapter.ViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener : onItemClickListener) {

        mListener = listener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cell_rayon, viewGroup, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rayon = rayons.get(position)
        holder.textViewName.text=rayon.title

    }

    class ViewHolder(view:View, listener: onItemClickListener) :RecyclerView.ViewHolder(view){
        val textViewName = view.findViewById<Button>(R.id.textViewTitle)

        init {
            textViewName.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }


    override fun getItemCount(): Int {
        return rayons.size
    }

}
