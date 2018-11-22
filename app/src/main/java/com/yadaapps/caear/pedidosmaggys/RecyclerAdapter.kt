package com.yadaapps.caear.pedidosmaggys

import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.menus.view.*

class RecyclerAdapter(var list: MutableList<Upload>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.menus,parent,false)
    return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItems(data:Upload){
            val cant =itemView.edCant

            val btn=itemView.btn_agregarMenu

            val text = data.mimageUrl
            Picasso.get().load(text).into(itemView.imagenMenu)

            itemView.setOnClickListener{
                Toast.makeText(itemView.context,"probate${text}",Toast.LENGTH_LONG).show()
            }
        }

    }

}
