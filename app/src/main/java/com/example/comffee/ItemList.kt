package com.example.comffee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.comffee.databinding.ActivityHomepageBinding
import com.example.comffee.databinding.ActivityItemListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ItemList : AppCompatActivity() {

    private lateinit var binding: ActivityItemListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemArrayList: ArrayList<Item>
    private lateinit var itemAdapter: ItemAdapter
    private val firestore = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    val userData = firestore.collection("users").document(currentUser?.email.toString())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        recyclerView = findViewById(R.id.itemRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        itemArrayList = arrayListOf()

        itemAdapter = ItemAdapter(itemArrayList)

        recyclerView.adapter = itemAdapter

        EventChangeListener()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.icon_home->{
                    val intent = Intent(this, Homepage::class.java)
                    startActivity(intent)
                    // Biar gada transisi blink
                    overridePendingTransition(0, 0)
                }
                R.id.icon_profile->{
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                }
                R.id.icon_history->{
                    val intent = Intent(this, History::class.java)
                    startActivity(intent)
                }
                R.id.icon_order->{
                    val intent = Intent(this, ItemList::class.java)
                    startActivity(intent)
                }
                R.id.icon_shopping_cart->{
                    val intent = Intent(this, ItemList::class.java)
                    startActivity(intent)
                }
                R.id.icon_logout->{
                    auth.signOut()
                    val loginIntent = Intent(this, Login::class.java)
                    startActivity(loginIntent)
                }
            }
            true
        }

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