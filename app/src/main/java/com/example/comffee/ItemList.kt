package com.example.comffee

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ItemList : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemArrayList: ArrayList<Item>
    private lateinit var itemAdapter: ItemAdapter
    private val firestore = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    val userData = firestore.collection("users").document(currentUser?.email.toString())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        recyclerView = findViewById(R.id.itemRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        itemArrayList = arrayListOf()

        itemAdapter = ItemAdapter(itemArrayList)

        recyclerView.adapter = itemAdapter

        EventChangeListener()

    }

    private fun EventChangeListener() {

        firestore.collection("items").orderBy("nama_barang", Query.Direction.ASCENDING)
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
}