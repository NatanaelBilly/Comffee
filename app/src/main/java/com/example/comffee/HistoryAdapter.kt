package com.example.comffee

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HistoryAdapter(private val historyList: ArrayList<HistoryItem>) : RecyclerView.Adapter<HistoryAdapter.MyHistoryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_history, parent,false)
        return MyHistoryViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    override fun onBindViewHolder(holder: MyHistoryViewHolder, position: Int) {
        val currentItem = historyList[position]
        holder.harga.text = currentItem.harga.toString()
        val resId = holder.itemView.context.resources.getIdentifier(
            currentItem.imagePath,
            "drawable",
            holder.itemView.context.packageName
        )
        if (resId == 0) {
            Log.d(ContentValues.TAG, "Cannot find image with name ${currentItem.imagePath} in drawable folder")
        } else {
            Glide.with(holder.itemView.context)
                .load(resId)
                .placeholder(R.drawable.image_not_supported)
                .error(R.drawable.image_not_supported)
                .into(holder.imagePath)
        }
        holder.namaBarang.text = currentItem.nama_barang
        holder.itemId.text = currentItem.item_id
        holder.qty.text = currentItem.qty.toString()
    }

    class MyHistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val harga: TextView = itemView.findViewById(R.id.tvharga)
        val imagePath: ImageView = itemView.findViewById(R.id.imgItem)
        val namaBarang: TextView = itemView.findViewById(R.id.tvnamaBarang)
        val itemId: TextView = itemView.findViewById(R.id.tvitemId)
        val qty: TextView = itemView.findViewById(R.id.tvqty)

    }
}