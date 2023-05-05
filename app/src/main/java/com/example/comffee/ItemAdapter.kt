package com.example.comffee

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val itemList : ArrayList<Item>) : RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemAdapter.MyViewHolder, position: Int) {

        val item : Item = itemList[position]
        holder.itemId.text = item.item_id
        holder.namaBarang.text = item.nama_barang
        holder.harga.text = item.harga.toString()

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    public class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val itemId : TextView = itemView.findViewById(R.id.tvitemId)
        val namaBarang : TextView = itemView.findViewById(R.id.tvnamaBarang)
        val harga : TextView = itemView.findViewById(R.id.tvharga)
    }
}