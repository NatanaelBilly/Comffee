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

    private val auth = FirebaseAuth.getInstance()

    private val firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {

            val loginIntent = Intent(this, Homepage::class.java)
            startActivity(loginIntent)
        }
    }

}