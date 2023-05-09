package com.example.comffee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.comffee.databinding.ActivityHistoryBinding
import com.example.comffee.databinding.ActivityHomepageBinding
import com.example.comffee.databinding.ActivityItemListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class History : AppCompatActivity() {

    //    private lateinit var dbReference : DatabaseReference
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyArrayList: ArrayList<HistoryItem>
    private lateinit var historyAdapter: HistoryAdapter
    private val firestore = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    val userData = firestore.collection("users").document(currentUser?.email.toString())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnBack.setOnClickListener {
//
//            val loginIntent = Intent(this, Homepage::class.java)
//            startActivity(loginIntent)
//        }
        historyRecyclerView = findViewById(R.id.historyRecyclerView)
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.setHasFixedSize(true)

        historyArrayList = arrayListOf<HistoryItem>()
        showHistory()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.icon_home -> {
                    val intent = Intent(this, Homepage::class.java)
                    startActivity(intent)
                }
                R.id.icon_history -> {
                    val intent = Intent(this, History::class.java)
                    startActivity(intent)
                    // Biar gada transisi blink
                    overridePendingTransition(0, 0)
                }
                R.id.icon_order -> {
                    val intent = Intent(this, ItemList::class.java)
                    startActivity(intent)
                }
                R.id.icon_logout -> {
                    auth.signOut()
                    val loginIntent = Intent(this, Login::class.java)
                    startActivity(loginIntent)
                }
            }
            true
        }
    }

    private fun showHistory() {
//        dbReference = FirebaseDatabase.getInstance().getReference("users")
//
//        dbReference.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
        userData.collection("transaksi")
            .orderBy("nama_barang", Query.Direction.ASCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }

                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            historyArrayList.add(dc.document.toObject(HistoryItem::class.java))
                        }
                    }
                    historyRecyclerView.adapter = HistoryAdapter(historyArrayList)
                }

            })
    }


}