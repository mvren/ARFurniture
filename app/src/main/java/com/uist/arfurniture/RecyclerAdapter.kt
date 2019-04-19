package com.uist.arfurniture

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val count = 4

    private val names = arrayOf("Chair","Table","Couch","Bed")
    private val models = arrayOf("VHAHIN_WOODEN_CHAIR.sfb","Table_Large_Rectangular_01.sfb","couch.sfb","Bed_01.sfb")
    private val imgs = arrayOf(R.drawable.abc_btn_check_material,R.drawable.abc_btn_check_material,R.drawable.abc_btn_check_material,R.drawable.abc_btn_check_material)


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var item_img: ImageView = itemView.findViewById(R.id.image)
        var item_name: TextView = itemView.findViewById(R.id.image_text)
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
    }
}