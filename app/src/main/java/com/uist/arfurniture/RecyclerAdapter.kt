package com.uist.arfurniture

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.icon_layout.view.*

class RecyclerAdapter (private val clickListener: (Uri) -> Unit) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val count = 4

    private val names = arrayOf("Chair","Table","Couch","Bed")
    private val models = arrayOf("chair.sfb","masa.sfb","couch.sfb","Bed_01.sfb")
    private val imgs = arrayOf(R.drawable.ic_chair,R.drawable.ic_table,R.drawable.ic_sofa,R.drawable.ic_bed)


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var item_img: ImageView = itemView.image
        var item_name: TextView = itemView.image_text

    }


    override fun getItemCount(): Int {
        return count
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.icon_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item_name.text = names[position]
        holder.item_img.setImageResource(imgs[position])
        holder.item_img.setOnClickListener { clickListener(Uri.parse(models[position]))}
    }
}