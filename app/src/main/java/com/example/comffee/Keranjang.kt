package com.example.comffee

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.comffee.databinding.ActivityKeranjangBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class Keranjang : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityKeranjangBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemArrayList: ArrayList<Item>
    private lateinit var itemCartAdapter: ItemCartAdapter
    private lateinit var cartTotal: TextView
    private lateinit var alamatValue: TextView
    private val firestore = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    val userData = firestore.collection("users").document(currentUser?.email.toString())
    private lateinit var item_id :String
    private lateinit var nama_barang: String
    private lateinit var harga: String
    private lateinit var imagePath: String
    private lateinit var qty: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKeranjangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(R.layout.activity_keranjang)
        cartTotal = findViewById(R.id.cartTotal)
        alamatValue = findViewById(R.id.alamatValue)

        supportActionBar?.hide()

        recyclerView = findViewById(R.id.rvItemCart)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        itemArrayList = arrayListOf()

        itemCartAdapter = ItemCartAdapter(itemArrayList)

        recyclerView.adapter = itemCartAdapter

//        binding.cartBuy.setOnClickListener(this)

        GlobalScope.launch(Dispatchers.Main) {
            getTotal()
        }

        val cartBuy: Button = findViewById(R.id.cartBuy)
        cartBuy.setOnClickListener {
            addToTransaksi()
        }

//        binding.cartBuy.setOnClickListener {
//            addToTransaksi()
//        }

        val backButton: ImageButton = findViewById(R.id.back_btn)
        backButton.setOnClickListener {
            val intent = Intent(this, ItemList::class.java)
            startActivity(intent)
        }

        userData.get()
            .addOnSuccessListener {
                // set username
                val profil = "${it.data?.get("address").toString()}"
                alamatValue.text = profil

            }
            .addOnFailureListener {
                Log.e("Firestore error!", it.message.toString())
            }

        EventChangeListener()

    }

    private fun EventChangeListener() {
        userData.collection("keranjang")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            itemArrayList.add(dc.document.toObject(Item::class.java))
                        }
                    }

                    itemCartAdapter.notifyDataSetChanged()
                }
            })
    }

    private suspend fun getTotal() = withContext(Dispatchers.IO) {

        val keranjangRef = FirebaseFirestore.getInstance()
            .collection("users")
            .document(currentUser?.email.toString())
            .collection("keranjang")

        keranjangRef.get()
            .addOnSuccessListener { documents ->
                var totalHarga = 0.0

                for (doc in documents) {
                    val harga = doc.getDouble("harga")
                    val qty = doc.getDouble("qty")
                    if (harga != null && qty != null) {
                        totalHarga += harga * qty
                    }
                }
                val totalHargaFormatted = String.format("Rp%,.0f,-", totalHarga)
                cartTotal.text = totalHargaFormatted
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents: ", exception)
            }
    }

    private fun buyProduct() {

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.cartBuy -> {
                buyProduct()
                val intent = Intent(this, Homepage::class.java)
                startActivity(intent)
            }
        }
    }
    private fun addToTransaksi() {
        userData.collection("keranjang").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list: MutableList<String> = ArrayList()
                    for (document in task.result) {
                        userData.collection("keranjang").document(document.id)
                            .get()
                            .addOnSuccessListener {
                                item_id = it.data?.get("item_id").toString()
                                nama_barang = it.data?.get("nama_barang").toString()
                                // nanti ubah ke double
                                harga = it.data?.get("harga").toString()
                                imagePath = it.data?.get("imagePath").toString()
                                // nanti ubah ke double
                                qty = it.data?.get("qty").toString()

                                val item = hashMapOf(
                                    "item_id" to item_id,
                                    "nama_barang" to nama_barang,
                                    "harga" to harga,
                                    "imagePath" to imagePath,
                                    "qty" to qty
                                )

                                userData.collection("transaksi").document()
                                    .set(item)
                                    .addOnSuccessListener {
                                        println("Sukses! Transaksi telah ditambahkan ke firestore")
                                    }
                                    .addOnFailureListener {
                                        println("Error")
                                    }
                                userData.collection("keranjang").document(item_id).delete()
                                    .addOnSuccessListener {
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(ContentValues.TAG, "Error adding document", e)
                                    }
                            }
                            .addOnFailureListener {
                                Log.e("Firestore error!", it.message.toString())
                            }
                    }
                    Log.d(ContentValues.TAG, list.toString())
                    val intent = Intent(this, Homepage::class.java)
                    startActivity(intent)
                } else {
                    Log.d(ContentValues.TAG, "Error getting documents: ", task.exception)
                }
            }
    }
}
