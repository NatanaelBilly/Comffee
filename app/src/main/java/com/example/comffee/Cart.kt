package com.example.comffee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.comffee.databinding.ActivityCartBinding
import com.google.firebase.firestore.*

private  lateinit var binding: ActivityCartBinding
private lateinit var recyclerView: RecyclerView
private lateinit var itemArrayList: ArrayList<Item>
private lateinit var itemAdapter: ItemAdapter
private val firestore = Firebase.firestore
private val auth = FirebaseAuth.getInstance()
private val currentUser = auth.currentUser
val userData = firestore.collection("users").document(currentUser?.email.toString())

class Cart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        binding = ActivityCartBinding.inflate(layoutInflater)

        recyclerView = findViewById(R.id.rvItemCart)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        itemArrayList = arrayListOf()

        itemAdapter = ItemAdapter(itemArrayList)

        recyclerView.adapter = itemAdapter

        readAddress()
        EventChangeListener()
    }
}

private fun EventChangeListener() {

    firestore.collection("cart").orderBy("nama_barang", Query.Direction.ASCENDING)
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

                itemAdapter.notifyDataSetChanged()
            }

        })

}

private fun readAddress() {

    userData.get()
        .addOnSuccessListener {
            // set username
            val alamat ="${it.data?.get("address").toString()}"
            binding.alamatValue.text = alamat

        }
        .addOnFailureListener {
            Log.e("Firestore error!", it.message.toString())
        }
}