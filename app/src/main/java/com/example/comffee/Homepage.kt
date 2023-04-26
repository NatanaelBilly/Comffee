package com.example.comffee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.comffee.databinding.ActivityHomepageBinding
import com.example.comffee.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class Homepage : AppCompatActivity() {

    private lateinit var binding: ActivityHomepageBinding

    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogout.setOnClickListener {
            auth.signOut()

            val loginIntent = Intent(this@Homepage, Login::class.java)
            startActivity(loginIntent)
        }
    }
}