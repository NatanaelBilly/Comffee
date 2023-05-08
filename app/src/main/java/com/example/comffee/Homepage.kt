package com.example.comffee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.comffee.databinding.ActivityHomepageBinding
import com.example.comffee.databinding.ActivityMainBinding
import com.google.api.ResourceDescriptor.History
import com.google.firebase.auth.FirebaseAuth

class Homepage : AppCompatActivity() {

    private lateinit var binding: ActivityHomepageBinding

    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.icon_home->{
                    val intent = Intent(this, Homepage::class.java)
                    startActivity(intent)
                    // Biar gada transisi blink
                    overridePendingTransition(0, 0)
                }
                R.id.icon_history->{
                    val intent = Intent(this, com.example.comffee.History::class.java)
                    startActivity(intent)
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
//        binding.btnHome.setOnClickListener {
//
//            val loginIntent = Intent(this@Homepage, Homepage::class.java)
//            startActivity(loginIntent)
//        }
//
//        binding.btnProfile.setOnClickListener {
//
//            val loginIntent = Intent(this@Homepage, Profile::class.java)
//            startActivity(loginIntent)
//        }
//
//        binding.btnHistory.setOnClickListener {
//
//            val loginIntent = Intent(this@Homepage, com.example.comffee.History::class.java)
//            startActivity(loginIntent)
//        }
//
//        binding.btnOrder.setOnClickListener {
//
//            val loginIntent = Intent(this@Homepage, Order::class.java)
//            startActivity(loginIntent)
//        }
//
//        binding.btnLogout.setOnClickListener {
//            auth.signOut()
//
//            val loginIntent = Intent(this@Homepage, Login::class.java)
//            startActivity(loginIntent)
//        }
    }
}