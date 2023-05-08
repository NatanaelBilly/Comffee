package com.example.comffee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.comffee.databinding.ActivityItemListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Keranjang : AppCompatActivity() {

    private lateinit var binding: ActivityItemListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemArrayList: ArrayList<Item>
    private lateinit var itemCartAdapter: ItemCartAdapter
    private val firestore = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    val userData = firestore.collection("users").document(currentUser?.email.toString())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        recyclerView = findViewById(R.id.rvItemCart)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        itemArrayList = arrayListOf()

        itemCartAdapter = ItemCartAdapter(itemArrayList)

        recyclerView.adapter = itemCartAdapter

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
}