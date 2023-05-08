package com.example.comffee

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ItemCartAdapter(private val itemList: ArrayList<Item>) : RecyclerView.Adapter<ItemCartAdapter.MyViewHolder>() {
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    val userData = db.collection("users").document(currentUser?.email.toString())

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ItemCartAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.keranjang, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemCartAdapter.MyViewHolder, position: Int) {
        val item: Item = itemList[position]
        holder.item_id.text = item.item_id
        holder.namaBarang.text = item.nama_barang
        holder.harga.text = item.harga.toString()

        val resId = holder.itemView.context.resources.getIdentifier(
            item.imagePath,
            "drawable",
            holder.itemView.context.packageName
        )
        if (resId == 0) {
            Log.d(ContentValues.TAG, "Cannot find image with name ${item.imagePath} in drawable folder")
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

        val namaBarang: TextView = itemView.findViewById(R.id.item_nama)
        val item_id: TextView = itemView.findViewById(R.id.item_description)
        val harga: TextView = itemView.findViewById(R.id.item_price)
        val quantity : EditText = itemView.findViewById(R.id.tvqty)
        val quantityButton: ImageButton = itemView.findViewById(R.id.btnSubmitQuantity)
        val removeButton: ImageButton = itemView.findViewById(R.id.btnRemoveFromShoppingCart)
        val imageView: ImageView = itemView.findViewById(R.id.cartFnbPic)

        init {
            quantityButton.setOnClickListener {
                val qty = quantity.text.toString().trim()
                val itemPosition = adapterPosition
                val clickedItem = itemList[itemPosition]
                println("ini qty: $quantity")
                val item = hashMapOf(
                    "item_id" to clickedItem.item_id,
                    "nama_barang" to clickedItem.nama_barang,
                    "harga" to clickedItem.harga,
                    "imagePath" to clickedItem.imagePath,
                    "qty" to qty.toDouble())

//                val cartCollection = db.collection("carts")
                val cartCollection = userData.collection("keranjang").document(clickedItem.item_id.toString())
                cartCollection.set(item)
                    .addOnSuccessListener {
                        Toast.makeText(itemView.context, "quantity changed to cart", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Log.w(ContentValues.TAG, "Error adding document", e)
                    }
            }
            removeButton.setOnClickListener {
                val qty = quantity.text.toString().trim()
                val itemPosition = adapterPosition
                val clickedItem = itemList[itemPosition]

//                val cartCollection = db.collection("carts")
                val cartCollection = userData.collection("keranjang").document(clickedItem.item_id.toString())
                cartCollection.delete()
                    .addOnSuccessListener {
                        Toast.makeText(itemView.context, "Deleted from cart", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Log.w(ContentValues.TAG, "Error adding document", e)
                    }
            }
        }
    }
}