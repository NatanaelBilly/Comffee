package com.example.comffee

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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

        supportActionBar?.hide()

        recyclerView = findViewById(R.id.rvItemCart)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        itemArrayList = arrayListOf()

        itemCartAdapter = ItemCartAdapter(itemArrayList)

        recyclerView.adapter = itemCartAdapter

//        binding.cartBuy.setOnClickListener(this)
//        GlobalScope.launch(Dispatchers.Main) {
//            getTotal()
//        }
        binding.cartBuy.setOnClickListener {
            addToTransaksi()
        }
        EventChangeListener()
    }

    private fun EventChangeListener() {
        userData.collection("keranjang").orderBy("nama_barang", Query.Direction.ASCENDING)
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

//    private suspend fun getTotal() {
//        var harga: Double
//        var qty: Double
//        var total = 0.0
//        firestore.collection("user")
//            .document(currentUser?.email.toString())
//            .collection("keranjang")
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val result = task.result
//                    if (result != null) {
//                        for (document in task.result!!) {
//                            firestore.collection("user")
//                                .document(currentUser?.email.toString())
//                                .collection("keranjang")
//                                .document(document.id)
//                                .get()
//                                .addOnCompleteListener {
//                                    harga = document.data?.get("harga").toString().toDouble()
//                                    qty = document.data?.get("qty").toString().toDouble()
//                                    println("jumlah barang: $qty")
//                                    total += harga * qty
//                                    binding.cartTotal.text = total.toString()
//                                }
//                        }
//                    }
//                    println("total: $total")
//                }
//            }
//            .await()
//    }

    private suspend fun getTotal() = withContext(Dispatchers.IO) {
        var harga: Double
        var qty: Double
        var total = 0.0
        try {
            val querySnapshot = firestore.collection("user")
                .document(currentUser?.email.toString())
                .collection("keranjang")
                .get()
                .await()

            println("This is query size: ${querySnapshot.documents.size}")

            for (document in querySnapshot.documents) {
                val documentSnapshot = firestore.collection("user")
                    .document(currentUser?.email.toString())
                    .collection("keranjang")
                    .document(document.id)
                    .get()
                    .await()

                val documentData = documentSnapshot.data
                if (documentData != null) {
                    harga = documentData["harga"].toString().toDouble()
                    qty = documentData["qty"].toString().toDouble()
                    println("jumlah barang: $qty")
                    total += harga * qty
                }
            }
            withContext(Dispatchers.Main) {
                binding.cartTotal.text = total.toString()
                println("total: $total")
            }
        } catch (e: Exception) {
            // handle the exception here
            println("Exception occurred: ${e.message}")
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

                                userData.collection("transaksi").document(item_id)
                                    .set(item)
                                    .addOnSuccessListener {
                                        println("Sukses! Transaksi telah ditambahkan ke firestore")
                                    }
                                    .addOnFailureListener {
                                        println("Error")
                                    }
                            }
                            .addOnFailureListener {
                                Log.e("Firestore error!", it.message.toString())
                            }
                    }
                    Log.d(ContentValues.TAG, list.toString())
                } else {
                    Log.d(ContentValues.TAG, "Error getting documents: ", task.exception)
                }
            }
    }
}
