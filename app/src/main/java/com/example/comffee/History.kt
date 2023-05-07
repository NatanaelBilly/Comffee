package com.example.comffee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.comffee.databinding.ActivityHistoryBinding
import com.example.comffee.databinding.ActivityHomepageBinding
import com.example.comffee.databinding.ActivityOrderBinding
import com.example.comffee.databinding.ActivityProfileBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class History : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemArrayList: ArrayList<Item>
    private lateinit var itemAdapter: ItemAdapter
    private val firestore = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    val userData = firestore.collection("users").document(currentUser?.email.toString())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.icon_home->{
                    val intent = Intent(this, Homepage::class.java)
                    startActivity(intent)
                }
                R.id.icon_profile->{
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                }
                R.id.icon_history->{
                    val intent = Intent(this, com.example.comffee.History::class.java)
                    startActivity(intent)
                    // Biar gada transisi blink
                    overridePendingTransition(0, 0)
                }
                R.id.icon_order->{
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

//        binding.btnBack.setOnClickListener {
//
//            val loginIntent = Intent(this, Homepage::class.java)
//            startActivity(loginIntent)
//        }
    }

}