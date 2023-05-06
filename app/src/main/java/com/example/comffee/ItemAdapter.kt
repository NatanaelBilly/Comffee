package com.example.comffee

import android.content.ContentValues.TAG
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.File

class ItemAdapter(private val itemList: ArrayList<Item>) :
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    val userData = db.collection("users").document(currentUser?.email.toString())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemAdapter.MyViewHolder, position: Int) {

        val item: Item = itemList[position]
        holder.itemId.text = item.item_id
        holder.namaBarang.text = item.nama_barang
        holder.harga.text = item.harga.toString()

        val resId = holder.itemView.context.resources.getIdentifier(
            item.imagePath,
            "drawable",
            holder.itemView.context.packageName
        )
        if (resId == 0) {
            Log.d(TAG, "Cannot find image with name ${item.imagePath} in drawable folder")
        } else {
            Glide.with(holder.itemView.context)
                .load(resId)
                .placeholder(R.drawable.image_not_supported)
                .error(R.drawable.image_not_supported)
                .into(holder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemId: TextView = itemView.findViewById(R.id.tvitemId)
        val namaBarang: TextView = itemView.findViewById(R.id.tvnamaBarang)
        val harga: TextView = itemView.findViewById(R.id.tvharga)
        val addButton: ImageButton = itemView.findViewById(R.id.btnAddToShoppingCart)
        val imageView: ImageView = itemView.findViewById(R.id.imgItem)

        init {
            addButton.setOnClickListener {
                val itemPosition = adapterPosition
                val clickedItem = itemList[itemPosition]
                val item = hashMapOf("item" to clickedItem,"qty" to 1)

//                val cartCollection = db.collection("carts")
                val cartCollection = userData.collection("keranjang")
                cartCollection.add(item)
                    .addOnSuccessListener {documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        Toast.makeText(itemView.context, "Added to cart", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }
        }
    }
}