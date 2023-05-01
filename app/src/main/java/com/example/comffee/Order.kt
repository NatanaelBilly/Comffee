package com.example.comffee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.comffee.databinding.ActivityHomepageBinding
import com.example.comffee.databinding.ActivityOrderBinding
import com.google.firebase.auth.FirebaseAuth

class Order : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            auth.signOut()

            val loginIntent = Intent(this, Homepage::class.java)
            startActivity(loginIntent)
        }
    }
}